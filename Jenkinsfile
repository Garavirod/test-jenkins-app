pipeline {
    agent any

    environment {
        NETLIFY_SITE_ID = '54e35e03-bfb2-4761-a8dc-5f1e71ba1ef0'
        NETLIFY_AUTH_TOKEN = credentials('netlify_token')
    }

    stages {
        stage("Build") {
            agent {
                docker {
                    image 'node:18-alpine'
                    reuseNode true // For syncronnizyng Workspaces (resue the same)
                }
            }
            steps{
                sh '''
                    ls -la
                    node --version
                    npm --version
                    npm ci
                    npm run build
                    ls -la
                '''
            }
        }
        stage("Test") {
            parallel {
                stage('Unit Tests'){
                    agent {
                        docker {
                            image 'node:18-alpine'
                            reuseNode true // For syncronnizyng Workspaces (resue the same)
                        }
                    }

                    steps {
                        sh '''
                            test -f build/index.html
                            npm test
                        '''
                    }

                    post {
                        always {
                            junit 'test-results/junit.xml'
                        }
                    }
                }
                stage("E2E") {
                    agent {
                        docker {
                            image 'mcr.microsoft.com/playwright:v1.39.0-jammy'
                            reuseNode true // For syncronnizyng Workspaces (resue the same)
                        }
                    }
                    steps {
                        sh '''
                            npm i serve
                            node_modules/.bin/serve -s build &
                            sleep 10
                            npx playwright test --reporter=html
                        '''
                    }

                    post {
                        always {
                            publishHTML([
                                allowMissing: false, 
                                alwaysLinkToLastBuild: false, 
                                keepAll: false, 
                                reportDir: 'playwright-report', 
                                reportFiles:'index.html', 
                                reportName:'Playwright HTML loacl', 
                                reportTitles:'', 
                                useWrapperFileDirectly:true])
                        }
                    }
                }

            }
        }
        stage("Deploy") {
            agent {
                docker {
                    image 'node:18-alpine'
                    reuseNode true // For syncronnizyng Workspaces (resue the same)
                }
            }
            steps {
                sh '''
                    npm install netlify-cli
                    node_modules/.bin/netlify --version
                    echo "Deploy into production. Site Id: $NETLIFY_SITE_ID"
                    node_modules/.bin/netlify status
                    node_modules/.bin/netlify deploy --dir=build --prod
                '''
            }
        }

        stage("Prod E2E") {
            agent {
                docker {
                    image 'mcr.microsoft.com/playwright:v1.39.0-jammy'
                    reuseNode true // For syncronnizyng Workspaces (resue the same)
                }
            }

            environment {
                CI_ENVIRONMENT_URL = 'https://ephemeral-jalebi-87b0ca.netlify.app'
            }

            steps {
                sh '''
                    npx playwright test --reporter=html
                '''
            }

            post {
                always {
                    publishHTML([
                        allowMissing: false, 
                        alwaysLinkToLastBuild: false, 
                        keepAll: false, 
                        reportDir: 'playwright-report', 
                        reportFiles:'index.html', 
                        reportName:'Playwright E2E', 
                        reportTitles:'', 
                        useWrapperFileDirectly:true])
                }
            }
        }
    }
   
}