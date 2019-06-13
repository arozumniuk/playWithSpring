#!/bin/bash
###########################################################################################
# @Synopsis: run docker-compose for functions test on local workstation                   #
# @See : https://docs.docker.com/compose/reference/overview/                              #
# --------------------------------------------------------------------------------------- #
# @Copyright RetailMeNot, 2019                                                            #
###########################################################################################

cd ..
mvn clean package -PskipTests
cd functional-tests
docker-compose -f ./docker-compose-LOCAL.yml down
docker-compose -f ./docker-compose-LOCAL.yml build --no-cache
docker-compose -f ./docker-compose-LOCAL.yml up -d --force-recreate

attempt_counter=0
max_attempts=10

until $(curl --output /dev/null --silent --head --fail http://localhost:8081/healthcheck); do
    if [ ${attempt_counter} -eq ${max_attempts} ];then
      echo "Max attempts to get http://localhost:8082/healthcheck not respond is reached. "
      exit 10
    fi

    printf '.'
    attempt_counter=$(($attempt_counter+1))
    sleep 5
done
