pipeline {
    agent any

    tools {
        nodejs 'nodejs' // Replace 'NodeJS' with your configured NodeJS installation name
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'b86e90bb-0df1-498f-98e3-f6a37443fcaf', url: 'https://github.com/kameshwaranvelu/demo']])
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'npm install'
            }
        }

        stage('Docker Build and Push') {
            steps {
                script {
                    def dockerImage = docker.build('app-name:01')
                    dockerImage.push()
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl create namespace your-namespace'
                sh 'kubectl apply -f kubernetes/service_and_deployment.yaml -n your-namespace'
                // Additional Kubernetes resources if needed (Ingress, ConfigMaps, etc.)
            }
        }
    }
}
