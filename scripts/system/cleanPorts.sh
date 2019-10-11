#!/usr/bin/env bash

#Cleaning ports of BE

sudo kill `sudo lsof -t -i:8081 -i:8080`