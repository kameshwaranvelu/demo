pipeline {
    agent any

    tools {
        nodejs 'nodejs' // Replace 'NodeJS' with your configured NodeJS installation name
        docker 'docker' // Use the Docker tool configured in Jenkins
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

        stage('Run Tests') {
            steps {
                sh 'npm test'
                // You can add test reporting or result parsing here
            }
        }

        stage('Build') {
            steps {
                sh 'npm run build' // Replace with your build command if different
                // Additional build steps if needed
            }
        }

        stage('Docker Build and Push') {
            steps {
                script {
                    def appImage = docker.build('app-image:01')
                    appImage.push()
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
