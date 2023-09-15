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
                    env.APP_NAME = 'sluv-back-office'
                    env.VERSION = env.BUILD_ID
                    env.ROOT_KEY = '151286422786'
                    env.ECR_URL = '151286422786.dkr.ecr.ap-northeast-2.amazonaws.com'
                    env.IMAGE_NAME = 'sluv-back-office'
                    env.ECR_REGION = 'ap-northeast-2'
                    env.CODE_DEPLOY_S3_BUCKET_NAME = 'cicd-sluv-back-office'
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

                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-sluv-back-office',
                    accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                    secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                ]]) {
                    sh "aws ecr get-login-password --region ${ECR_REGION} | docker login --username AWS --password-stdin ${ECR_URL}"
                    sh "docker build -t ${IMAGE_NAME}:${VERSION} ."
                    sh "docker tag ${IMAGE_NAME}:${VERSION} ${ECR_URL}/${IMAGE_NAME}:${VERSION}"
                    sh "docker tag ${IMAGE_NAME}:${VERSION} ${ECR_URL}/${IMAGE_NAME}:latest"

                    echo "[DEV] Docker Build - docker image to ECR push"
                    sh "docker push ${ECR_URL}/${IMAGE_NAME}:${VERSION}"
                    sh "docker push ${ECR_URL}/${IMAGE_NAME}:latest"
                }
            }
        }

        stage("[DEV] Deploy") {
            steps {
                echo "[DEV] Deploy"

                fileOperations([
                  // 배포 디렉토리 삭제
                  folderDeleteOperation('build/deploy'),
                  // 배포 디렉토리 생성
                  folderCreateOperation('build/deploy'),
                  // CodeDeploy 에서 사용할 scripts 디렉토리 복사
                  folderCopyOperation(sourceFolderPath: 'scripts', destinationFolderPath: 'build/deploy/scripts'),
                  // 빌드된 Application 의 결과물 복사
                  fileCopyOperation(excludes: '', flattenFiles: true, includes: 'build/libs/*.jar', targetLocation: 'build/deploy'),
                  // CodeDeploy 에서 사용할 appspec 파일 복사
                  fileCopyOperation(excludes: '', flattenFiles: true, includes: 'appspec.yml', targetLocation: 'build/deploy')
                ])

                dir("./build/deploy") {
                    withCredentials([[
                        $class: 'AmazonWebServicesCredentialsBinding',
                        credentialsId: 'aws-sluv-back-office',
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                    ]]) {
                        step([$class: 'AWSCodeDeployPublisher',
                            applicationName: "${APP_NAME}",
                            awsAccessKey: "${AWS_ACCESS_KEY_ID}",
                            awsSecretKey: "${AWS_SECRET_ACCESS_KEY}",
                            credentials: 'awsAccessKey',
                            deploymentConfig: 'CodeDeployDefault.OneAtATime',
                            deploymentGroupAppspec: false,
                            deploymentGroupName: 'dev',
                            deploymentMethod: 'deploy',
                            excludes: '',
                            iamRoleArn: '',
                            includes: '**',
                            proxyHost: '',
                            proxyPort: 0,
                            region: "${ECR_REGION}",
                            s3bucket: "${CODE_DEPLOY_S3_BUCKET_NAME}",
                            s3prefix: '',
                            subdirectory: '',
                            versionFileName: '',
                            waitForCompletion: true,
                            pollingTimeoutSec: 1800
                        ])
                    }
                }
            }
        }


    }
}