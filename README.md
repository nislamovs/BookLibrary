# BookLibrary
Book library project.

Status : Development

#### Stack:

    Embedded Tomcat with https 
    Springboot 2
    Spring Security + JWT
    Spring Data (audit, revisions...)
    Logback
    Orika Mapper
    Lombok
    Swagger
    Docker, DockerCompose
    Liquibase
    Booklibrary_BE
    Angular7

#### How to build:
 
**Backend:**

- Go to `Booklibrary_BE` directory and run command `./gradlew clean build` or `gradle clean build`.

- Then go to `BookLibrary/Booklibrary_BE/build/` directory and find there file `booklibrary-1.0.0.jar`.

- Run it with `java -jar booklibrary-1.0.0.jar`.

- The service will launch on `8443` or `7443` port [prod/dev respectively]. Endpoints will be accessible from `https://localhost:8443/api/`.

- Spring actuator endpoints will be available from `https://localhost:8443/actuator`.

- H2 in-memory storage is available from `https://localhost:8443/h2`.

- Backend could be launched via console ```./gradlew bootRun``` or from IDE as well. 

**Frontend:**

- Go to `Booklibrary_FE` and run `ng serve`.
 
- Open browser, type `localhost:4200` in url field and You'll see frontend part.

**API Docs:**

- Swagger ui will be accessible from `https://localhost:8443/swagger-ui.html`.

- Swagger json will be accessible from `https://localhost:8443/api/swagger.json`.

- If You need API docs in pdf/html format, run: ```./gradlew clean swaggerDoc```. Then go to ```BookLibrary/Booklibrary_BE/build/asciidoc```, where You will find documentation in both formats.

**Docker:** 

- To build docker image, go to project root directory and there run ```./gradlew clean build docker```

- To export image in separate tar archive, go to _docker scripts directory_ ```BookLibrary/scripts/docker```, and there run script ```create_image.sh```. You will find docker image (in tar format) in build directory: ```BookLibrary/Booklibrary_BE/build/docker```.

- To run all service (FE and BE) run ```docker-compose up```  

#### Certificates:

If You want to regenerate certificates, You should visit folder `Scripts/system/certificates` and 
there run script `generateCertificates.sh`. If You want to change key params, You should change those in script.