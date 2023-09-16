#!/bin/bash

# 컨테이너 개수 확인
container_count=$(docker ps -q | wc -l)

# 실행 중인 컨테이너가 하나도 없을 경우에만 실행
if [ $container_count -gt 0 ]; then
  docker stop $(docker ps -a -q)
else
  echo "No running containers to stop."
fi