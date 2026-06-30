pipeline {
    agent any

    triggers {
        githubPush()
    }

    environment {
        DOCKER_IMAGE = 'calculator'
        REGISTRY = 'docker.io'
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
                sh 'chmod +x gradlew && ./gradlew compileJava'
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Running unit tests...'
                sh 'chmod +x gradlew && ./gradlew test'
            }
        }

        stage('Package') {
            steps {
                echo 'Building JAR package...'
                sh 'chmod +x gradlew && ./gradlew build'
            }
        }

        stage('Acceptance Test') {
            steps {
                echo 'Running acceptance tests...'
                sh "docker stop ${DOCKER_IMAGE} || true"
                sh "docker rm ${DOCKER_IMAGE} || true"
                sh "docker run -d -p 8765:8080 --name ${DOCKER_IMAGE} ${DOCKER_IMAGE}"
                sh 'sleep 5'
                sh "chmod +x gradlew && ./gradlew acceptanceTest -Dcalculator.url=http://localhost:8765"
            }
        }

        stage('Docker Build & Push') {
            steps {
                echo 'Building and pushing Docker image...'
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-credentials',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh """
                        docker build -t ${DOCKER_IMAGE} -t \$DOCKER_USERNAME/${DOCKER_IMAGE}:${BUILD_NUMBER} -t \$DOCKER_USERNAME/${DOCKER_IMAGE}:latest .
                        echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin
                        docker push \$DOCKER_USERNAME/${DOCKER_IMAGE}:${BUILD_NUMBER}
                        docker push \$DOCKER_USERNAME/${DOCKER_IMAGE}:latest
                    """
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
