# Vending Machine

This is a simple Spring Boot REST API for a vending machine, 
allowing users to perform various operations like creating products, insert coins and more.

## Prerequisites for local development

To run this project, you need to have the following software installed on your system:

- Java 17
- Maven 3.9.0
- Spring Boot 3.1.5
- Docker

## Installation

### Building the Docker Image

Clone the repository to your local machine:

    git clone https://github.com/dayradzhieva/vending.machine

Navigate to the project directory:

    cd vending.machine

Run this Maven command to build the project:

    mvn clean install

Build the Docker image by running the following command:

    docker build -t vending.machine:0.1 .

### Running the Docker Container

To run the Spring Boot application as a Docker container, use the following command:

    docker run -p 8080:8080 vending.machine:0.1
The application will start in a Docker container, and you can access it at http://localhost:8080.

## Swagger Documentation

The API is documented using Swagger.
You can access the Swagger documentation to learn more about the available endpoints and their details by visiting,
it is more convenient to test the application using Swagger UI than Postman:

    http://localhost:8080/swagger-ui/index.html#

## API Endpoints

The API provides the following endpoints:

- POST /vending-machine/products: Create a product.
- PUT /vending-machine/products/{productName}: Update a product.
- DELETE /vending-machine/products/{productName}: Delete a product.
- GET /vending-machine/products/list: Get all available products.
- PUT /vending-machine/coins/{coin}: Insert a coin.
- DELETE /vending-machine/coins/: Return all inserted coins.
- POST /vending-machine/buy/{productName}: Buy a product.

### Examples
Here are some example requests and responses for the API:

- POST /vending-machine/products:
```
  {
  "name": "Chips",
  "price": 1.2,
  "quantity": 6
  }
  ```
- PUT /vending-machine/products/Chips:
```
  {
  "name": "Chips",
  "price": 1.5,
  "quantity": 6
  }
  ```
- PUT /vending-machine/coins/FIFTY_COINS:
  Response (200 OK): 0.5 (Return the current balance for user)


    