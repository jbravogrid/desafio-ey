# desafio-ey
# Getting Started

### Guides to run with gradle
The following guides illustrate how to use this artifac with gradle

* go to path of the project
* gradlew build
* gradle bootRun

### Guides to run with docker
The following guides illustrate how to use this artifac with docker

* go to path of the project
* gradlew build
* docker build  -t desafio/api-user:1.0.0 .
* docker run --name=api-user -d -p 8080:8080 desafio/api-userr:1.0.0
