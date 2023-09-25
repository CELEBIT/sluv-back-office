# docker login
aws ecr get-login-password --region ap-northeast-2 | sudo docker login --username AWS --password-stdin 151286422786.dkr.ecr.ap-northeast-2.amazonaws.com

# pull docker image
if [ "$DEPLOYMENT_GROUP_NAME" == "dev" ]
then
  sudo docker rm -f codedeploy
  sudo docker rmi sluv-back-office
  sudo docker pull sluv-back-office:latest
fi
#elif [ "$DEPLOYMENT_GROUP_NAME" == "stage" ]
#then
#  docker-compose -f /deploy/docker-compose.stage.yml pull
#elif [ "$DEPLOYMENT_GROUP_NAME" == "production" ]
#then
#  docker-compose -f /deploy/docker-compose.yml pull
