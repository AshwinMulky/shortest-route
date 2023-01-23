# Shortest Route

Spring boot api to `calculate any possible land route from one country to another`. The objective is to take a list of country data in JSON format and calculate the route by utilizing individual countries border information.

## Tech
- Java 8
- Spring boot
- Breadth First Search Algorithm

## Prerequisites
- At least Java 8
- Maven

## Compile and Package
Below command will generate the `shortest-route-0.0.1-SNAPSHOT.jar` file at `target` folder
```sh
mvn clean package
```

## Execute
Run the below command
```sh
java -jar target/shortest-route-0.0.1-SNAPSHOT.jar
```
Wait till the application gets initialized.

```sh
2023-01-23T11:56:20.667+05:30  INFO 25404 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 9001 (http) with context path ''
2023-01-23T11:56:20.680+05:30  INFO 25404 --- [           main] com.routing.ShortestRouteApplication     : Started ShortestRouteApplication in 5.208 seconds (process running for 5.579)
```

Application will be running on `9001` port in the `localhost`

## Test the api
Hit the URL in any browser
```sh
http://localhost:9001/routing/CZE/ITA
```