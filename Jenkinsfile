#!groovy
env.submitter = ''
env.projectid = ''

pipeline {
    agent any

    stages {
        stage("App Build") {
            steps {
                script {
                    println("************[App Build]************")
                    sh("./gradlew clean build")

                }
            }
        }

        stage("Docker Build & Push") {
            steps {
                script {
                    println("************[Docker Build & Push]************")
                    sh("aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 151286422786.dkr.ecr.ap-northeast-2.amazonaws.com")
                    sh("docker build -t sluv-back-office-dev .")
                    sh("docker tag sluv-back-office-dev:latest 151286422786.dkr.ecr.ap-northeast-2.amazonaws.com/sluv-back-office-dev:latest")
                    sh("docker push 151286422786.dkr.ecr.ap-northeast-2.amazonaws.com/sluv-back-office-dev:latest")
                }
            }
        }

    }
}