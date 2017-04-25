# API Challenge

...

Operations:
 
GET /dogpictures
GET /dogpictures/name/{petName}
GET /dogpictures/breed/{petBreed}
GET /dogpictures/breed/grouped
GET /dogpictures/id/{id}
PUT /dogpictures/voting/petId/{id}/vote/{op}  where {op} should be "up" or "dn"


## Dependencies

spring-dbc
spring-mvc
...
See the POM file for details


## Building

```
$ mvn package or mvnw package
```

## Running

```
$ java -jar target/api_interview-0.1.0.jar or
 mvn spring-boot:run
```