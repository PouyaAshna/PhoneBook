# PhoneBook
## Purpose
The purpose of this program is to create a phonebook that includes lists of Github repositories of each contact you can 
also search for a contact in the phonebook.  
Since GitHub API might not be responsive all the time for reasons like poor connection and etc, 
CircuitBreaker pattern is used to overcome this issue.  
In this project, all the GitHub API calls are done async so that there is no problem to save the contact.  
Since a series of requests may be failed, those failed requests are stored in MongoDB and called again according to The specified schedule.   
Since the number of unauthenticated requests on GitHub is limited to 60 requests per hour, RateLimiter is used to overcome this limitation.
## Building and deploying the application
## Testing the application
To run test cases of the project execute the following command:

```bash
  ./gradlew test
```

### Building the application
The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build the project execute the following command:

```bash
  ./gradlew build -x test
```

### Running the application
Create docker image:

```bash
  docker-compose build
```
Run docker image:

```bash
  docker-compose up -d
```

To see docker status:

```bash
  docker ps
```

You should get a response like this:
```
CONTAINER ID        IMAGE                 COMMAND                  CREATED             STATUS              PORTS                    NAMES
01104f966f9c        phonebook_phonebook   "ash -c 'java -jar *…"   6 seconds ago       Up 5 seconds        0.0.0.0:8080->8080/tcp   phonebook
e85ee8c74db1        mysql:8.0.14          "docker-entrypoint.s…"   7 seconds ago       Up 6 seconds        3306/tcp, 33060/tcp      phonebook-mysql
3bbeafbf61e6        mongo:4.4             "docker-entrypoint.s…"   7 seconds ago       Up 6 seconds        27017/tcp                phonebook-mongo

```

To see application logs:

```bash
  docker logs -f phonebook
```

To stop application:
```bash
  docker-compose stop
```
This will start the API container exposing the application's port (set to `8080` in this app) you should wait until 
mysql and mongo come up.
### Demo application
You can go to [DemoSwaggerURL](http://185.235.41.51:8080/swagger-ui.html#) and see online swagger also you can test your 
apis on this ip

## Technology stack & other Open-source libraries
### Data
* 	[MongoDB](https://www.mongodb.com/2) - Cross-platform document-oriented database program
* 	[MySQL](https://www.mysql.com/) - Open-Source Relational Database Management System
* 	[H2 Database Engine](https://www.h2database.com/html/main.html) - Java SQL database. Embedded and server modes; in-memory databases

### Server - Backend

* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[Gradle](https://gradle.org) - Dependency Management

###  Libraries and Plugins
* 	[Resilience4j](https://resilience4j.readme.io) - Resilience4j is a lightweight, easy-to-use fault tolerance library inspired by
                                           Netflix Hystrix
* 	[Lombok](https://projectlombok.org/) - Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.
* 	[Swagger](https://swagger.io/) - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.
* 	[MapStruct](https://mapstruct.org) - Object Mapper
* 	[RestAssured](https://rest-assured.io) - Java library that provides a domain-specific language (DSL) for writing powerful, maintainable tests for RESTful APIs
* 	[JavaFaker](https://github.com/DiUS/java-faker) - is a library that can be used to generate a wide array of real-looking data from addresses to popular culture references.

## Documentation
* 	[JavaDoc](https://pouyaashna.github.io/PhoneBook/) - ``` https://pouyaashna.github.io/PhoneBook/ ``` - Java Documentation
* 	[LocalSwagger](http://localhost:8080/swagger-ui.html) - ``` http://localhost:8080/swagger-ui.html ``` - Documentation & Testing
* 	[ReleaseSwagger](http://185.235.41.51:8080/swagger-ui.html#) - ``` http://185.235.41.51:8080/swagger-ui.html# ``` - Documentation & Testing
