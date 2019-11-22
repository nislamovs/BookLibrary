#!/usr/bin/env bash

#Here will be temporarily stored useful scripts for work

docker run -d -p 27018:27017 mongo

mongodump --host=localhost --port=27017 --db=booklibrary

mongorestore --host=localhost --port=27018 --db=booklibrary ./booklibrary/