pipeline {
    agent any

    stages {
        stage('No Docker') {
            steps {
                sh '''
                    echo "Without Docker"
                    touch no-docker.txt
                    ls -la
                '''
            }
        }
        stage('Docker') {
            agent {
                docker {
                    image 'node:22-alpine'
                    reuseNode true
                }
            }
            steps {
                sh '''
                    touch yes-docker.txt
                    ls -la
                    echo "With Docker"
                    npm --version
                    
                '''
            }
        }
    }
}
