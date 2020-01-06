# DBPopulator service

### Description
Special microservice for BookLibrary API to fill database with data.

### Guides
The following guides illustrate how to use this microservice:

- Install docker
- Set env variables for mailtrap and slack in .profile and/or .bashrc
- Run command: ```./gradlew clean build docker```
- Run command ```docker-compose build``` and then ```docker-compose up```
- Goto ```localhost:9090/trigger/status``` to check what services were already processed

### Bugs / Issues
During development some issues were faced:

- Using Mongorel we can store combined documents (with orphans) only if we're using LAZY loading.
If we'll use EAGER loading, we will have an exception. Hovewer, if we want to retrieve data, we
can do this only using EAGER loading.  

