# Mock-RestfulAPI
Mock for Contacts Restful API

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org/install.html)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. 
One way is to execute the `main` method in the `src.main.java.mock.restful.contactsapi.ContactsApiApplication` class from your IDE.

Alternatively you can use the 
[Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) 
like so.

In terminal, go to project folder run this command to build and run:

```shell
mvn spring-boot:run
```

### Running the app with JAR file only

Build the JAR file:

```shell
mvn clean package
```

The result JAR file will locate in `target` with name `contacts-api-1.0.jar`. Run JAR file with command:

```shell
java -jar target/contacts-api-1.0.jar
```

