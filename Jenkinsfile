pipeline {
    agent any

    options {
        timestamps()
    }

    tools {
        maven 'Maven3'
        jdk 'JDK_26'
    }

    stages {
        stage('1. Checkout') {
            steps {
                checkout scm
                bat 'git log -1 --oneline'
            }
        }

        stage('2. Build & Test') {
            steps {
                bat 'mvn clean package'
            }
            post {
                always {
                    junit testResults: '**/target/surefire-reports/*.xml, **/target/failsafe-reports/*.xml'

                    // FIXED: Explicit paths for JaCoCo
                    jacoco(
                        execPattern: 'target/jacoco.exec',
                        classPattern: 'target/classes',
                        sourcePattern: 'src/main/java',
                        inclusionPattern: 'com/example/demo/**',
                        exclusionPattern: '**/*Test*'
                    )

                    script {
                        def unitReports = findFiles(glob: '**/target/surefire-reports/*.xml')
                        def integrationReports = findFiles(glob: '**/target/failsafe-reports/*.xml')
                        def unitCount = unitReports ? unitReports.length : 0
                        def integrationCount = integrationReports ? integrationReports.length : 0
                        echo "Unit tests: ${unitCount} | Integration tests: ${integrationCount}"
                    }
                }
                success { echo 'Build and tests passed' }
                failure { echo 'Build or test failures detected' }
            }
        }

        stage('3. Code Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat 'mvn sonar:sonar -Dsonar.projectKey=demo -Dsonar.host.url=http://localhost:9000'
                }
            }
        }

        stage('4. Quality Gate') {
            steps {
                timeout(time: 3, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('5. Docker Build') {
            steps {
                script {
                    if (!fileExists('Dockerfile')) {
                        error 'Dockerfile not found'
                    }
                    bat "docker build -t demo:${env.BUILD_ID} ."
                }
            }
        }

        stage('6. Deploy') {
            steps {
                bat 'docker stop demo-app 2>nul || exit 0'
                bat 'docker rm demo-app 2>nul || exit 0'
                bat "docker run -d --name demo-app -p 8081:8080 demo:${env.BUILD_ID}"
            }
        }
    }

    post {
        success { echo "Pipeline completed - Build: ${env.BUILD_ID}" }
        failure { echo 'Pipeline failed - check logs' }
    }
}