#!groovy

pipeline {
    agent any

//     environment {
//         AWS_ACCESS_KEY_ID = credentials('awsAccessKeyId')
//         AWS_SECRET_ACCESS_KEY = credentials('awsSecretAccessKey')
//         AWS_DEFAULT_REGION = 'ap-northeast-2'
//     }

    stages {
        stage("Set environment") {
            steps {
                echo "Set environment"
                script {
                    env.APP_NAME = ''
                    env.VERSION = env.BUILD_ID
                    env.ROOT_KEY = '151286422786'
                    env.ECR_URL = '151286422786.dkr.ecr.ap-northeast-2.amazonaws.com'
                    env.IMAGE_NAME = 'sluv-back-office'
                    env.ECR_REGION = 'ap-northeast-2'
                    env.cluster = ''
                    env.service = ''
                    env.appECS = ''
                    env.dgpDCS = ''
                }
            }
        }

        stage("AWS Configuration") {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-sluv-back-office',
                    accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                    secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                ]]) {
                    sh 'aws s3 ls'
                }
            }
        }

        stage("[DEV] Checkout") {
            steps {
                echo "[DEV] Checkout"
                checkout scm
            }
        }

        stage("[DEV] Gradle Build") {
            steps {
                echo "[DEV] Gradle Build"
                sh "./gradlew clean build"
                sh "ls build/libs/*.jar"
            }
        }

        stage("[DEV] Docker Build") {
            steps {
                echo "[DEV] Docker Build"
                sh "ls -al build/libs"

            }
        }




//         stage("Docker Build & Push") {
//             steps {
//                 script {
//                     println("************[Docker Build & Push]************")
//                     sh("sudo aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 151286422786.dkr.ecr.ap-northeast-2.amazonaws.com")
//                     sh("sudo docker build -t sluv-back-office-dev .")
//                     sh("sudo docker tag sluv-back-office-dev:latest 151286422786.dkr.ecr.ap-northeast-2.amazonaws.com/sluv-back-office-dev:latest")
//                     sh("sudo docker push 151286422786.dkr.ecr.ap-northeast-2.amazonaws.com/sluv-back-office-dev:latest")
//                 }
//             }
//         }

    }
}