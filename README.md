# MyPoS-Backend

This was done as a pet project to learn ReactJs alongside Spring Boot.
This SpringBoot application serves as the backend for MyPoS Client application.

You can find the API Documentation here..
https://app.swaggerhub.com/apis-docs/tj-at-bootcamp/mypos-api/1.0

### Prerequisites
* Git
* JDK 8 or later
* Maven 3.0 or later

### Clone
To get started you can simply clone this repository using git:
```
git clone https://github.com/tj-513/mypos-backend.git
cd mypos
```

### Configuration
In order to get the API working you have to provide the following settings:
```
spring.datasource.url=jdbc:mysql://myserver.com
spring.datasource.username=your_username
spring.datasource.password=your_password
```
This application will be listening to requests coming at http://localhost:8090 you can change the configurations as necessary.
The configuration is located in `src/resources/application.properties`.



### Build an executable JAR
You can run the application from the command line using:
```
mvn spring-boot:run
```
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:
```
mvn clean package
```
Then you can run the JAR file with:
```
java -jar target/*.jar
```

*Instead of `mvn` you can also use the maven-wrapper `./mvnw` to ensure you have everything necessary to run the Maven build.*
