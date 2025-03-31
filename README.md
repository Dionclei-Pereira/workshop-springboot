![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-green)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-3.4-green)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4.1-blue)

# Spring Boot Workshop

## 📖 Description

This project implements a backend. The main objective is to create an API that manages a web workshop.

## 🚀 **Technologies**

The main technologies used in this project are:

- ✅ Java 21 
- ✅ Spring Boot  
- ✅ Hibernate  
- ✅ H2 DataBase
- ✅ Spring Security
- ✅ JWT - RSA

## 🎯 **Features**
- ✅ Users  
- ✅ Authentication
- ✅ Orders  
- ✅ Products

## ⚙ Prerequisites

Install these programs:

- **Java 21**
- **OpenSSL**
- **IDE** (IntelliJ IDEA, Eclipse, VSCode.)
- **Postman** (or similar.)

## ⚡ Steps to Run the Project

### 1. Clone the repository

Clone the project to your local environment:

```bash
git clone https://github.com/Dionclei-Pereira/workshop-springboot.git
cd workshop-springboot
```
### 2. Configure RSA keys

To set up RSA encryption, you will need to generate a key pair (private and public keys) using OpenSSL.

1. Generate the private key (`private_key.pem`):
```bash
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
```
2. Generate the public key (`public_key.pem`):
   
```bash
openssl rsa -pubout -in private_key.pem -out public_key.pem
```

### 3. Run the Project

To run the project, you can use your IDE or Maven CLI:

### 4. Testing the API

The API is configured to allow login and generate a JWT token. You can use **Postman** to test the routes.

- **POST** `/auth/login`: Send an `email` and `password` to receive a JWT token.
- **GET** `/products`: This route is protected and requires a valid JWT token in the Authorization header.

Example request for login:

POST /auth/login
```json
{
  "email": "email@gmail.com",
  "password": "password"
}
```

If the login is successful, a JWT token will be returned.

Example request to access a protected route:

- **GET** `/products` <br>
Authorization: _your-jwt-token_

## 📑 API Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/auth/login` | Authenticates a user and returns a JWT token | `{ "email": "email@gmail", "password": "passw" }` | `{ "token": "eyJhbGciOiJIUzUxMiJ9..." }` |
| `POST` | `/auth/register` | Authenticates a new user | `{"email": "email@gmail.com","phone": 999999999,"name": "username","password": "password"}` | `200 OK`<br>`400 Bad Request` |
| `GET`  | `/categories` | Retrieves all categories | - | `200 OK` |
| `GET`  | `/categories/{id}` | Retrieves a specific category by ID | - | `200 OK`<br>`404 Not Found`|
| `GET (ADMIN)`  | `/orders` | Retrieves all orders | - | `200 OK` |
| `GET`  | `/orders/{id}` | Retrieves a specific order by ID (if you're not an admin, you can only see yours)| - | `200 OK`<br>`404 Not Found`<br>`403 Forbidden` |
| `GET `  | `/products` | Retrieves all products | - | `200 OK` |
| `GET`  | `/products/{id}` | Retrieves a specific product by ID| - | `200 OK`<br>`404 Not Found` |
| `POST (ADMIN)` | `/products` | Creates a new product | `{"name": "Smartphone X", "description": "Latest generation.", "price": 899.99, "categories": [1,3]}` | `201 Created`<br>`400 Bad Request`<br>`403 Forbidden`|
| `GET (ADMIN)`  | `/users` | Retrieves all users | - | `200 OK`<br>`403 Forbiden` |
| `GET`  | `/users/{id}` | Retrieves a specific user by ID (if you're not an admin, you can only see yours)| - | `200 OK`<br>`404 Not Found`<br>`403 Forbiden` |
| `PUT`  | `/users/{id}` | Updates a user by ID | `{ "name": "John Updated", "email": "john.updated@example.com" }` | `200 OK`<br>`400 Bad Request`<br>`404 Not Found` |
| `DELETE (ADMIN)` | `/users/{id}` | Deletes a user by ID | - | `204 No Content`<br>`403 Forbidden` |
| `GET`  | `/users/{id}/orders` | Retrieves all orders from a user by ID (if you're not an admin, you can only see yours)| - | `200 OK`<br>`404 Not Found`<br>`403 Forbiden` |
| `PUT`  | `/users/{id}/orders` | add an order to a user by ID (if you're not an admin, you can only add to yours)| `{"orderItems": [{ "productId": 3, "quantity": 1}]}` | `200 OK`<br>`404 Not Found`<br>`403 Forbiden` |

## 📜Author

**Dionclei de Souza Pereira**

[Linkedin](https://www.linkedin.com/in/dionclei-de-souza-pereira-07287726b/)

⭐️ If you like this project, give it a star!  
