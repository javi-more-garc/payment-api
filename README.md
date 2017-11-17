# payment-api

This application exposes a sample payment API.

## Requirements to run the application

* Java 8
* Maven
* MongoDB database (not needed for tests)

## Building the application

We just run:

> mvn clean install

## Running the application

First we need to configure the properties of the environment to tun.

If we want to run in development mode we'd configure application-dev.properties.

Then we can run the application in development mode with:

> mvn spring-boot:run -DSPRING_PROFILES_ACTIVE=dev

The application (swagger) should be up and kicking under 8080 port:

> http://localhost:8080

## Running the tests

We only have Integration Tests so we can run:

> mvn clean verify

The serenity index TDD report will be generated in target/site/serenity/index.html



 