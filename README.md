# Demo GenDev Backend

A Java application scaffold for backend development demonstrations.

## Project Overview

* A simple Java application built with Gradle
* Uses Java 21
* Demonstrates basic project structure with a "Hello World" functionality
* Part of the POC Colombia project for Scotia Tech

## Setup and Configuration

### Requirements

* JDK 21
* Gradle 9.0.0 or compatible (wrapper included)

### Dependencies

* Google Guava (33.4.6-jre) - A set of core Java libraries
* JUnit (4.13.2) - For unit testing

### Building and Running

To build the project:

```bash
./gradlew build
```

To run the application:

```bash
./gradlew run
```

The application will output "Hello World!" to the console when executed.

### Running Tests

Execute the test suite with:

```bash
./gradlew test
```

## Project Structure

* `app/src/main/java/org/example/App.java` - Main application entry point
* `app/src/test/java/org/example/AppTest.java` - Unit tests
* `gradle/libs.versions.toml` - Dependency version management

## Development

This project uses Gradle's configuration cache for better build performance. The project is configured to use the Foojay Resolver Convention for automatic JDK management.

## Contact

* Project maintained by Scotia Tech team
* For support, contact the POC Colombia project team
