# Demo GenDev Backend

A Spring Boot RESTful API backend for development demonstrations.

## Project Overview

* A Spring Boot application built with Gradle
* Uses Java 21
* Implements a REST API with CRUD operations
* Part of the POC Colombia project for Scotia Tech

## Setup and Configuration

### Requirements

* JDK 21
* Gradle 9.0.0 or compatible (wrapper included)

### Dependencies

* Spring Boot Starter (3.2.5) - Core Spring Boot functionality
* Spring Boot Starter Web (3.2.5) - Web application support with embedded Tomcat
* Spring Boot Starter Test (3.2.5) - Testing support for Spring Boot applications
* Google Guava (33.4.6-jre) - A set of core Java libraries

### Building and Running

To build the project:

```bash
./gradlew build
```

To run the application:

```bash
./gradlew bootRun
```

The application will start a Spring Boot server on port 8080.

### Running Tests

Execute the test suite with:

```bash
./gradlew test
```

## Project Structure

* `app/src/main/java/org/example/App.java` - Spring Boot application entry point
* `app/src/main/java/org/example/controller/` - REST controllers
* `app/src/main/java/org/example/model/` - Data models
* `app/src/main/resources/application.properties` - Spring Boot configuration
* `app/src/test/java/org/example/AppTest.java` - Unit tests
* `gradle/libs.versions.toml` - Dependency version management

## Development

This project uses Gradle's configuration cache for better build performance. The project is configured to use the Foojay Resolver Convention for automatic JDK management.

## API Endpoints

The following REST endpoints are available:

* `GET /api/hello` - Returns a simple greeting message
* `GET /api/messages` - Returns all messages
* `GET /api/messages/{id}` - Returns a specific message by ID
* `POST /api/messages` - Creates a new message
* `PUT /api/messages/{id}` - Updates an existing message
* `DELETE /api/messages/{id}` - Deletes a message

## Contact

* Project maintained by Scotia Tech team
* For support, contact the POC Colombia project team
