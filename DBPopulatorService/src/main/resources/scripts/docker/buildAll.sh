#!/usr/bin/env bash

cd ../../../../

./gradlew clean build docker;
docker-compose build;
docker-compose up
