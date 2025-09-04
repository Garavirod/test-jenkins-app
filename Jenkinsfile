pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh '''
                   echo "Building application"
                '''
            }
            docker {
                image 'node:22-alpine'
                reuseNode true
            }
            steps {
                sh '''
                   echo "Node version"
                   node --version
                   echo "NPM version"
                   npm --version
                   npm ci
                   npm run build
                   ls -la
                '''
            }
        }
    }
}
