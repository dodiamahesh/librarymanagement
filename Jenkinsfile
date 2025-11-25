pipeline {

    agent any

    environment {
        IMAGE_NAME = "librarymanagement"
        CONTAINER_NAME = "librarymanagement"
        DOCKERHUB_CREDENTIALS = 'dockerhub-cred'   // Add in Jenkins Credentials
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "Pulling Latest Code from GitHub..."
                git branch: 'master',
                    credentialsId: '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',
                    url: 'https://github.com/dodiamahesh/librarymanagement.git'
            }
        }

        stage('Maven Clean & Build') {
            steps {
                echo "Running Maven Build..."
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker Image..."
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Stop & Remove Old Container') {
            steps {
                echo "Stopping old container if exists..."
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                """
            }
        }

        stage('Run New Docker Container') {
            steps {
                echo "Starting new container..."
                sh """
                    docker run -d -p 9090:9090 --name ${CONTAINER_NAME} ${IMAGE_NAME}
                """
            }
        }
    }

    post {
        success {
            echo "Pipeline Completed Successfully."
        }
        failure {
            echo "Pipeline Failed."
        }
    }
}
