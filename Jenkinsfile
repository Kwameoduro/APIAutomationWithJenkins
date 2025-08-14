pipeline {
	agent { label 'PascoChewer' }

    environment {
		JAVA_HOME = "${tool name: 'JDK17', type: 'hudson.model.JDK'}"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
		stage('Environment Info') {
			steps {
				sh 'java -version || echo "Java not installed"'
                sh 'mvn -version || echo "Maven not installed"'
            }
        }

        stage('Install Java & Maven') {
			steps {
				sh '''
                    echo "Installing Java 17 and Maven..."
                    sudo apt-get update -y
                    sudo apt-get install -y openjdk-17-jdk maven
                    java -version
                    mvn -version
                '''
            }
        }

        stage('Checkout Code') {
			steps {
				checkout scm
            }
        }

        stage('Build Project') {
			steps {
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
				slackSend(
                    channel: env.SLACK_CHANNEL,
                    color: 'good',
                    message: "✅ Build #${env.BUILD_NUMBER} succeeded! Check the report: ${env.BUILD_URL}"
                )
                emailext(
                    subject: "✅ Build #${env.BUILD_NUMBER} SUCCESS",
                    body: "The build succeeded.\nCheck the report here: ${env.BUILD_URL}",
                    to: env.EMAIL_RECIPIENTS
                )
            }
        }
        failure {
			script {
				slackSend(
                    channel: env.SLACK_CHANNEL,
                    color: 'danger',
                    message: "❌ Build #${env.BUILD_NUMBER} failed. Check logs: ${env.BUILD_URL}"
                )
                emailext(
                    subject: "❌ Build #${env.BUILD_NUMBER} FAILED",
                    body: "The build failed.\nCheck logs here: ${env.BUILD_URL}",
                    to: env.EMAIL_RECIPIENTS
                )
            }
        }
    }
}
