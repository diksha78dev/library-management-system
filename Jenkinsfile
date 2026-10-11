
pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    options {
        disableConcurrentBuilds()
    }

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'staging', 'prod'],
            description: 'Select the target environment'
        )
    }

    environment {
        DOCKER_IMAGE = 'library-management-system'
        JUNIT_PUBLISHED = 'false'
    }

    stages {
        stage('1. Checkout & Environment Audit') {
            steps {
                deleteDir()
                checkout scm

                script {
                    env.COMMIT_SHA = sh(
                        script: 'git rev-parse --short HEAD',
                        returnStdout: true
                    ).trim()

                    env.COMMIT_AUTHOR = sh(
                        script: "git log -1 --pretty=format:'%an <%ae>'",
                        returnStdout: true
                    ).trim()
                }

                sh '''
                    echo "Java version:"
                    java -version
                    echo "Maven version:"
                    mvn -version
                    echo "Commit:"
                    git rev-parse HEAD
                    echo "Commit author: ${COMMIT_AUTHOR}"
                    echo "Selected environment: ${ENVIRONMENT}"
                '''
            }
        }

        stage('2. Static Code Analysis & Linting') {
            steps {
                sh 'mvn -B -DskipTests compile'

                sh '''
                    mvn -B \
                    -Dcheckstyle.config.location=config/checkstyle/checkstyle.xml \
                    checkstyle:check
                '''
            }
        }

        stage('3. Automated Unit Testing') {
            steps {
                catchError(
                    buildResult: 'FAILURE',
                    stageResult: 'FAILURE'
                ) {
                    sh 'mvn -B test'
                }
            }
        }

        stage('4. Test Reporting & Quality Gate') {
            steps {
                junit(
                    testResults: 'target/surefire-reports/*.xml',
                    allowEmptyResults: true
                )

                archiveArtifacts(
                    artifacts: 'target/surefire-reports/**',
                    allowEmptyArchive: true
                )

                script {
                    env.JUNIT_PUBLISHED = 'true'
                }
            }
        }

        stage('5. JaCoCo Coverage Enforcement') {
            when {
                expression {
                    currentBuild.currentResult == 'SUCCESS'
                }
            }
            steps {
                sh 'mvn -B verify -DskipTests'

                archiveArtifacts(
                    artifacts: 'target/site/jacoco/**',
                    allowEmptyArchive: false
                )
            }
        }

        stage('6. OWASP Dependency Security Scan') {
            when {
                expression {
                    currentBuild.currentResult == 'SUCCESS'
                }
            }
            steps {
                sh '''
                    mvn -B \
                    org.owasp:dependency-check-maven:12.1.0:check \
                    -DfailBuildOnCVSS=9.0
                '''
            }
        }

        stage('7. Package & Archive JAR') {
            when {
                expression {
                    currentBuild.currentResult == 'SUCCESS'
                }
            }
            steps {
                sh 'mvn -B package -DskipTests'

                archiveArtifacts(
                    artifacts: 'target/library-management-system-*.jar',
                    fingerprint: true
                )
            }
        }

        stage('8. Docker Build & Tag') {
            when {
                expression {
                    currentBuild.currentResult == 'SUCCESS'
                }
            }
            steps {
                script {
                    def image = docker.build(
                        "${env.DOCKER_IMAGE}:${env.BUILD_NUMBER}"
                    )

                    echo "Docker image built: ${image.imageName}"
                }
            }
        }
    }

    post {
        always {
            script {
                if (env.JUNIT_PUBLISHED != 'true') {
                    junit(
                        testResults: 'target/surefire-reports/*.xml',
                        allowEmptyResults: true
                    )
                }
            }

            archiveArtifacts(
                artifacts: 'target/dependency-check-report.*',
                allowEmptyArchive: true
            )

            echo """
            Build status: ${currentBuild.currentResult}
            Commit: ${env.COMMIT_SHA ?: 'unknown'}
            Commit author: ${env.COMMIT_AUTHOR ?: 'unknown'}
            Build URL: ${env.BUILD_URL}
            Environment: ${params.ENVIRONMENT}
            """

            script {
                if (env.NOTIFICATION_EMAIL?.trim()) {
                    emailext(
                        to: env.NOTIFICATION_EMAIL,
                        subject: "Jenkins ${env.JOB_NAME} #${env.BUILD_NUMBER}: ${currentBuild.currentResult}",
                        body: """Status: ${currentBuild.currentResult}
Commit author: ${env.COMMIT_AUTHOR ?: 'unknown'}
Build URL: ${env.BUILD_URL}
Environment: ${params.ENVIRONMENT}
"""
                    )
                }
            }
        }

        success {
            echo "SUCCESS: Build ${env.BUILD_NUMBER}; author=${env.COMMIT_AUTHOR}; URL=${env.BUILD_URL}"
        }

        failure {
            echo "FAILURE: Build ${env.BUILD_NUMBER}; author=${env.COMMIT_AUTHOR}; URL=${env.BUILD_URL}"
        }

        unstable {
            echo "UNSTABLE: Build ${env.BUILD_NUMBER}; author=${env.COMMIT_AUTHOR}; URL=${env.BUILD_URL}"
        }
    }
}
