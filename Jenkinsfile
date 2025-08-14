pipeline {
	agent { label 'PascoChewer' }

    tools {
		maven 'Maven-3.9.0' // Specify Maven version - adjust to your Jenkins tool configuration
        jdk 'JDK-17'        // Specify JDK 17 - adjust to your Jenkins tool configuration
        allure "PascoChewer"
    }

    environment {
		JAVA_HOME = tool('JDK-17')
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
		stage('Checkout Code') {
			steps {
				checkout scm
                // Verify Java version
                sh 'java -version'
                sh 'javac -version'
            }
        }

        stage('Build Project') {
			steps {
				sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
			steps {
				sh 'mvn clean test'
            }
            post {
				always {
					// Publish test results
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'

                    // Archive test reports
                    archiveArtifacts artifacts: 'target/surefire-reports/*.xml', allowEmptyArchive: true
                }
            }
        }

        stage('Publish Allure Report') {
			steps {
				allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
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
			// Clean workspace after build
            cleanWs()
        }
    }
}