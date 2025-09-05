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
            agent {
                docker dockerConfig.nodeJs()
            }
            steps {
                sh '''
                   test -f build/index.html
                   npm test
                '''
            }
        }
    }
}
