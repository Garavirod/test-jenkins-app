@Library('JenkinsDockerConfigLib') _
pipeline {
    agent any

    stages {
        stage('Build') {
            agent {
                docker dockerConfig.nodeJs()
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
            steps {
                docker dockerConfig.nodeJs()
                sh '''
                   test -f build/index.html
                   npm test
                '''
            }
        }
    }
}

def nodeDocker = [
    image: 'node:22-alpine',
    reuseNode: true   
]
