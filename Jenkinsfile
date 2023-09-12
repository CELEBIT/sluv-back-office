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
                }
            }
        }

    }
}