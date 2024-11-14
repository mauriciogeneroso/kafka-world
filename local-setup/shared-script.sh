#!/bin/sh

Green='\033[0;32m'
Yellow='\033[0;33m'
NC='\033[0m' # No Color

PREFIX="kafka-world"
DOCKER_COMPOSE_FILE=$1
APPLICATION_FOLDER=$2
SERVICES=$3

for service in "${SERVICES[@]}"
do
    DOCKER_IMAGE_ID=$(docker images -aq ${PREFIX}-${service})
    if [[ ! -z "${DOCKER_IMAGE_ID}" ]]; then

        LIVE_CONTAINER_ID=$(docker ps -a | grep `docker images | grep ${DOCKER_IMAGE_ID} | awk '{print $1":"$2}'` | awk '{print $1}')
        if [[ ! -z "${LIVE_CONTAINER_ID}" ]]; then
            printf "${Green}Removing existing container for ${Yellow}${service}${Green} with image id: ${Yellow}${DOCKER_IMAGE_ID}${NC}\n"
            docker rm -f "${LIVE_CONTAINER_ID}"
        fi

        printf "${Green}Removing existing docker image for ${Yellow}${service}${Green} with id: ${Yellow}${DOCKER_IMAGE_ID}${NC}\n"
        docker rmi "${DOCKER_IMAGE_ID}"
    fi
done

echo "${Green}Building ${Yellow}${APPLICATION_FOLDER}${NC}"
cd "../${APPLICATION_FOLDER}"
../gradlew clean build
cd ../local-setup

echo "${Yellow}Starting Docker Compose${NC}"
docker-compose -f $DOCKER_COMPOSE_FILE up -d --build
