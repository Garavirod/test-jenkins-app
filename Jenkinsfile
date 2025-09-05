pipeline {
    agent any

    stages {
        stage('Build') {
            agent {
                docker nodeDocker
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
        stage('Test') {
            agent {
                docker nodeDocker
            }
            steps {
                docker {
                    image 'node:22-alpine'
                    reuseNode true
                }
                sh '''
                   test -f build/index.html
                   npm test
                '''
            }
        }
    }
}

def nodeDocker = {
    docker {
        image 'node:22-alpine'
        reuseNode true
    }
}
