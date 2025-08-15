pipeline {
	agent any

    tools {
		allure "PascoChewer"
    }

    stages {
		stage('Checkout Code') {
			steps {
				checkout scm
            }
        }

        stage('Build Project') {
			steps {
				sh 'java -version'
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
			steps {
				script {
					// Run tests with testFailureIgnore to continue even if tests fail
                    sh 'mvn test -Dmaven.test.failure.ignore=true'
                }
            }
            post {
				always {
					script {
						// Debug: List what's in the target directory
                        sh '''
                            echo "=== Target directory contents ==="
                            ls -la target/ || echo "No target directory"
                            echo "=== Surefire reports directory ==="
                            ls -la target/surefire-reports/ || echo "No surefire-reports directory"
                            echo "=== XML files in surefire-reports ==="
                            ls -la target/surefire-reports/*.xml 2>/dev/null || echo "No XML files in surefire-reports"
                            echo "=== All XML files in target ==="
                            find target -name "*.xml" -type f || echo "No XML files found"
                        '''

                        // Publish test results - this will show failed tests in Jenkins UI
                        if (fileExists('target/surefire-reports')) {
							try {
								// Use publishTestResults for better handling
                                publishTestResults([
                                    testResultsPattern: 'target/surefire-reports/*.xml',
                                    mergeReruns: true,
                                    allowEmptyResults: false
                                ])
                            } catch (Exception e) {
								echo "publishTestResults not available, trying junit: ${e.getMessage()}"
                                try {
									junit(
                                        testResults: 'target/surefire-reports/*.xml',
                                        allowEmptyResults: false,
                                        skipPublishingChecks: true
                                    )
                                } catch (Exception e2) {
									echo "junit also failed: ${e2.getMessage()}"
                                }
                            }

                            // Archive all test artifacts
                            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
                        } else {
							echo "No surefire-reports directory found"
                        }

                        // Archive allure results if they exist
                        if (fileExists('target/allure-results')) {
							archiveArtifacts artifacts: 'target/allure-results/**/*', allowEmptyArchive: true
                        }
                    }
                }
            }
        }

        stage('Publish Allure Report') {
			steps {
				script {
					if (fileExists('target/allure-results')) {
						try {
							allure([
                                includeProperties: false,
                                jdk: '',
                                properties: [],
                                reportBuildPolicy: 'ALWAYS',
                                results: [[path: 'target/allure-results']]
                            ])
                        } catch (Exception e) {
							echo "Allure report generation failed: ${e.getMessage()}"
                        }
                    } else {
						echo "No Allure results found at target/allure-results"
                    }
                }
            }
        }
    }

    post {
		success {
			script {
				if (env.SLACK_CHANNEL) {
					slackSend(
                        channel: env.SLACK_CHANNEL,
                        color: 'good',
                        message: "✅ Build #${env.BUILD_NUMBER} succeeded! Check the report: ${env.BUILD_URL}"
                    )
                }
                if (env.EMAIL_RECIPIENTS) {
					emailext(
                        subject: "✅ Build #${env.BUILD_NUMBER} SUCCESS",
                        body: "The build succeeded.\nCheck the report here: ${env.BUILD_URL}",
                        to: env.EMAIL_RECIPIENTS
                    )
                }
            }
        }
        unstable {
			script {
				if (env.SLACK_CHANNEL) {
					slackSend(
                        channel: env.SLACK_CHANNEL,
                        color: 'warning',
                        message: "⚠️ Build #${env.BUILD_NUMBER} is unstable (some tests failed). Check the report: ${env.BUILD_URL}"
                    )
                }
                if (env.EMAIL_RECIPIENTS) {
					emailext(
                        subject: "⚠️ Build #${env.BUILD_NUMBER} UNSTABLE",
                        body: "The build completed but some tests failed.\nCheck the report here: ${env.BUILD_URL}",
                        to: env.EMAIL_RECIPIENTS
                    )
                }
            }
        }
        failure {
			script {
				if (env.SLACK_CHANNEL) {
					slackSend(
                        channel: env.SLACK_CHANNEL,
                        color: 'danger',
                        message: "❌ Build #${env.BUILD_NUMBER} failed. Check logs: ${env.BUILD_URL}"
                    )
                }
                if (env.EMAIL_RECIPIENTS) {
					emailext(
                        subject: "❌ Build #${env.BUILD_NUMBER} FAILED",
                        body: "The build failed.\nCheck logs here: ${env.BUILD_URL}",
                        to: env.EMAIL_RECIPIENTS
                    )
                }
            }
        }
        always {
			cleanWs()
        }
    }
}