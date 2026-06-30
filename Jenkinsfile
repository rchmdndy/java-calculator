pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'calculator'
        REGISTRY = 'docker.io'
        DOCKER_USERNAME = credentials('docker-hub-username')
        FULL_IMAGE_NAME = "${DOCKER_USERNAME}/${DOCKER_IMAGE}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling Java code...'
                sh './gradlew compileJava'
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Running unit tests...'
                sh './gradlew test'
            }
        }

        stage('Package') {
            steps {
                echo 'Building JAR package...'
                sh './gradlew build'
            }
        }

        stage('Acceptance Test') {
            steps {
                echo 'Running acceptance tests...'
                sh "docker run -d -p 8765:8080 --name ${DOCKER_IMAGE} ${DOCKER_IMAGE}"
                sh 'sleep 5'
                sh "./gradlew acceptanceTest -Dcalculator.url=http://localhost:8765"
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'
                sh "docker build -t ${DOCKER_IMAGE} -t ${FULL_IMAGE_NAME}:${BUILD_NUMBER} -t ${FULL_IMAGE_NAME}:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                echo 'Pushing Docker image...'
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-credentials',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin && docker push ${FULL_IMAGE_NAME}:${BUILD_NUMBER} && docker push ${FULL_IMAGE_NAME}:latest"
                }
            }
        }
    }

    post {
        success {
            echo 'Build and push successful!'
        }
        failure {
            echo 'Build failed. Check logs.'
        }
    }
}
