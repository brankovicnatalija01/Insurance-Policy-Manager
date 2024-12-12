# **Policy Manager App**

This project is a backend solution for managing insurance products, coverages, and policies for an insurance company. It provides RESTful APIs for:
- Insurance employees to manage products and their associated coverages.
- Customers to purchase insurance policies tailored to their needs.

The system enforces business rules like conditional coverage dependencies and discounts to ensure consistency and accuracy in policy configurations.

---

## **Key Features**
1. Manage insurance products (create, read, and search).
2. Configure coverages with rules (conditional dependencies and discounts).
3. Create and manage customer policies.
4. Calculate policy premiums based on selected coverages and applied discounts.
5. Restrict product modifications if linked to existing policies.
6. Search for policies by customer name or product.

---

## **Technologies**
- **Java 21**
- **Spring Boot**
- **SQLite** (Relational database)
- **Swagger UI** for API documentation
- **JUnit 5** for testing
- **Spring Boot Test with MockMvc** for integration testing
- **SLF4J with Logback** for logging

---

## **Business Rules**
1. **Conditional Coverage**  
   Some coverages can only be included if a specific required coverage is also included.  
   Example: "Coverage B" requires "Coverage A".

2. **Coverage Discount**  
   Discounts can be applied to a coverage if another specific coverage is included.  
   Example: "Coverage C" gets a 20% discount if "Coverage A" is included.

---

### API Documentation

#### Swagger UI
- Interactive Swagger documentation: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

#### Postman Collection
- Public Postman Collection: [Postman Link](https://www.postman.com/natalija12345678/homework/example/31644171-a102e181-0c1c-4e9d-af35-57beda90e5ff)

## **API Endpoints**

### **Insurance Product Endpoints**
- **POST** `/api/products`  
  Create a new insurance product.

- **GET** `/api/products/{id}`  
  Get details of an insurance product by ID.

- **GET** `/api/products/search?name={name}`  
  Search for products by name.

- **GET** `/api/products/all`  
  Get a list of all products.

- **DELETE** `/api/products/{id}`  
  Delete a product (if no linked policies).

---

### **Policy Endpoints**
- **POST** `/api/policies`  
  Create a new policy.

- **GET** `/api/policies/search?firstName={name}&lastName={name}&insuranceProductName={name}`  
  Search for policies by customer or product name.

- **GET** `/api/policies/all`  
  Retrieve all policies.

- **DELETE** `/api/policies/{id}`  
  Delete a policy.

---

## Logging

### Overview

The project uses **SLF4J** with **Logback** as the logging framework.

## Integration Testing

### Overview
Integration testing ensures that all components of the application work together as expected. For this project, integration tests were implemented using **Spring Boot Test** with the **MockMvc** framework to validate the behavior of the API endpoints.

## **Setup Instructions**

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-repo/insurance-api.git
   cd insurance-api
   
2. **Install Dependencies**
   ```bash
   mvn clean install
   
3. **Run the Application**
   ```bash
   mvn spring-boot:run

4. **Access Swagger UI**
   Open http://localhost:8080/swagger-ui/index.html in your browser
