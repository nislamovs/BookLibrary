#!/usr/bin/env bash

#Cleaning ports

sudo kill `sudo lsof -t -i:8081 -i:8080`