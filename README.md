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
* Gradle 8.7 or compatible (wrapper included)

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
* `app/src/main/java/org/example/service/` - Business logic services
* `app/src/main/java/org/example/repository/` - Data access repositories
* `app/src/main/java/org/example/model/` - Data models
* `app/src/main/java/org/example/dto/` - Data transfer objects
* `app/src/main/java/org/example/config/` - Application configuration
* `app/src/main/resources/application.properties` - Spring Boot configuration
* `app/src/main/resources/schema.sql` - Database schema definition
* `app/src/main/resources/data.sql` - Sample data for development
* `app/src/test/java/org/example/` - Unit and integration tests
* `gradle/libs.versions.toml` - Dependency version management

## Development

This project uses Gradle's configuration cache for better build performance. The project is configured to use the Foojay Resolver Convention for automatic JDK management.

## API Endpoints

The following REST endpoints are available:

### Authentication

* `POST /api/auth/login` - Authenticate user and generate token
* `GET /api/auth/validate` - Validate an authentication token
* `POST /api/auth/logout` - Logout user and invalidate token

### Messages

* `GET /api/messages` - Returns all messages
* `GET /api/messages/{id}` - Returns a specific message by ID
* `POST /api/messages` - Creates a new message
* `PUT /api/messages/{id}` - Updates an existing message
* `DELETE /api/messages/{id}` - Deletes a message

### Accounts

* `GET /api/accounts/{id}` - Get account by ID
* `GET /api/accounts/owner/{ownerId}` - Get all accounts for a specific owner
* `POST /api/accounts` - Create a new account
* `PUT /api/accounts/{id}` - Update an account
* `DELETE /api/accounts/{id}` - Delete an account
* `GET /api/accounts/{accountId}/transactions` - Get all transactions for an account
* `GET /api/accounts/{accountId}/transactions/last` - Get last N transactions for an account
* `GET /api/accounts/{accountId}/transactions/type/{type}` - Get transactions for an account by type
* `POST /api/accounts/{accountId}/transactions` - Add a new transaction to an account

### Credit Cards

* `GET /api/cards/{id}` - Get credit card by ID
* `GET /api/cards/{id}/dto` - Get credit card DTO by ID
* `GET /api/cards/owner/{ownerId}` - Get all credit cards for a specific owner
* `POST /api/cards` - Create a new credit card
* `PUT /api/cards/{id}` - Update a credit card
* `DELETE /api/cards/{id}` - Delete a credit card
* `GET /api/cards/{cardId}/transactions` - Get all transactions for a credit card
* `GET /api/cards/{cardId}/transactions/daterange` - Get transactions for a credit card by date range
* `GET /api/cards/{cardId}/transactions/type/{type}` - Get transactions for a credit card by type
* `POST /api/cards/{cardId}/transactions` - Add a new transaction to a credit card

### Transactions

* `GET /api/transactions/{id}` - Get a specific transaction
* `GET /api/transactions/card/{creditCardId}` - Get all transactions for a credit card
* `GET /api/transactions/client/{clientId}` - Get all transactions for a client
* `GET /api/transactions/card/{creditCardId}/daterange` - Get transactions for a card by date range
* `GET /api/transactions/client/{clientId}/card/{creditCardId}/daterange` - Get client's card transactions by date range
* `GET /api/transactions/card/{creditCardId}/type/{type}` - Get transactions for a card by type
* `GET /api/transactions/client/{clientId}/card/{creditCardId}/type/{type}` - Get client's card transactions by type
* `POST /api/transactions/card/{creditCardId}` - Create a new transaction
* `DELETE /api/transactions/{id}` - Delete a transaction

## Contact

* Project maintained by Scotia Tech team
* For support, contact the POC Colombia project team
