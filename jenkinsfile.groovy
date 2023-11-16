pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'Passkey@098', url: 'https://github.com/kameshwaranvelu/demo'
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    def npmCache = tool name: 'NodeJS', type: 'npm'
                    sh "${npmCache}/bin/npm install"
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    def npmCache = tool name: 'NodeJS', type: 'npm'
                    sh "${npmCache}/bin/npm test"
                }
                // You can add test reporting or result parsing here
            }
        }

        stage('Build') {
            steps {
                script {
                    def npmCache = tool name: 'NodeJS', type: 'npm'
                    sh "${npmCache}/bin/npm run build" // Replace with your build command
                }
                // Additional build steps if needed
            }
        }

        stage('Docker Build and Push') {
            steps {
                script {
                    docker.withRegistry('https://hub.docker.com/repository/docker/kameshwaranvelu/sampleapp/', 'Kamesh@123') {
                        def appImage = docker.build('app-name:01')
                        appImage.push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl create namespace app-1'
                sh 'kubectl apply -f kubernetes/service_and_deployment.yaml -n app-1'
                // Additional Kubernetes resources if needed (Ingress, ConfigMaps, etc.)
            }
        }
    }
}
