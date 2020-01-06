#!/usr/bin/env bash

./gradlew clean build docker;
#docker network prune;
docker-compose build;
docker-compose up