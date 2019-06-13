#!/bin/bash
###########################################################################################
# @Synopsis: run docker-compose for functions test on Test environment                  #
# @See : https://docs.docker.com/compose/reference/overview/                              #
# --------------------------------------------------------------------------------------- #
# @Copyright RetailMeNot, 2019                                                            #
###########################################################################################

docker-compose -f ./docker-compose-TEST.yml up -d --force-recreate

