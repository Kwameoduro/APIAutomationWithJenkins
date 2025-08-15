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
					// Run tests but don't fail the build on test failures
                    // This allows Jenkins to process the test results
                    try {
						sh 'mvn test'
                    } catch (Exception e) {
						echo "Some tests failed, but continuing to process results..."
                        echo "Error: ${e.getMessage()}"
                        // Set build as unstable instead of failed
                        currentBuild.result = 'UNSTABLE'
                    }
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
                            echo "=== All XML files in target ==="
                            find target -name "*.xml" -type f || echo "No XML files found"
                        '''

                        // Try to publish test results
                        if (fileExists('target/surefire-reports/TEST-*.xml')) {
							junit 'target/surefire-reports/TEST-*.xml'
                            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
                        } else if (fileExists('target/surefire-reports/*.xml')) {
							junit 'target/surefire-reports/*.xml'
                            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
                        } else {
							echo "No JUnit XML test reports found"
                            // Archive any available test reports
                            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
                        }
                    }
                }
            }
        }

        stage('Publish Allure Report') {
			steps {
				script {
					if (fileExists('target/allure-results')) {
						allure([
                            includeProperties: false,
                            jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'target/allure-results']]
                        ])
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