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
				sh 'mvn test'
            }
            post {
				always {
					junit 'target/surefire-reports/*.xml'
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
			cleanWs()
        }
    }
}