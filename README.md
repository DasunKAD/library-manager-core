# Library Management System

## Introduction

Welcome to the Library Management System API! This project provides a comprehensive solution for managing library operations through a RESTful API. The API is designed to handle essential library functions efficiently and securely.

### Features

- **Borrower Management:** Register and manage borrowers to keep track of library members.
- **Book Management:** Register new books and maintain a detailed inventory.
- **Book Borrowing and Returning:** Seamlessly handle the process of borrowing and returning books.

### Authentication

The API uses **stateless authentication** with JSON Web Tokens (JWT) to ensure secure interactions. Each request requires a valid JWT, enabling secure and role-based access to API functionalities.

## Technical Stack

| Technology      | Version |
|-----------------|---------|
| Java            | 17      |
| Spring Boot     | 3.3.1   |
| Spring Data JPA | 3.3.1   |
| Spring Security | 6.3.0   |
| Postgres        | 12.0    |
| Docker          | 27.0.3  |
| Docker Compose  | 2.28.1  |
| JWT             | 0.11.5  |
| JUnit           | 5.11.0  |
| Mockito         | 5.12.0  |
| Maven           | 3.9.7   |

## Prerequisites
- Docker
- Git
- Postman (Optional for testing)

## Usage
1. Clone the project from the GitHub repository:
   git clone https://github.com/DasunKAD/library-manager-core.git
   Alternatively, you can download the project as a zip file and extract it.
2. Navigate to the root directory of the project in your terminal.
3. Run Docker Compose to start the containers for the application and Postgres database services:
- docker-compose up -d --build
- The application and Postgres database are containerized and managed with Docker Compose. The database and test data will be automatically created before starting the Spring Boot application.
- **Default user credentials as follows**

  | User Name            | Password |
  |----------------------|----------|
  | john.doe@example.com | 123      |
  | jane.doe@example.com | 123      |

- Docker container book-manager-app-main has two services, book-manager-app for app and book-manager-db for database. 

## Authentication

The Library Management System API employs Spring Security for handling authentication. It uses JSON Web Tokens (JWT) to manage user sessions and secure API access. Here's how authentication works:

1. **Obtain a JWT Token:**

   To authenticate and receive a JWT token, make a POST request to the `/api/v1/auth/authentication` endpoint with your credentials (e.g., username and password). If the credentials are valid, the API will respond with a JWT token.

2. **Include the JWT Token in Requests:**

   Once you have the JWT token, include it in the `Authorization` header of your API requests. The token should be formatted as follows:

   ```plaintext
   Authorization: Bearer <token>   
## Testing with postman
[Library-managment.postman_collection.json](doc%2FLibrary-managment.postman_collection.json) user this postman collection to test the entire system.

## API Documentation

Once  system is upand running Please visit [API Documentation http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Database Design
### Why PostgreSQL?

PostgreSQL is an excellent choice for the Library Management System due to its robust features, scalability, and reliability. Here’s why:

1. **Advanced SQL Compliance:**
   PostgreSQL offers advanced SQL compliance and support for complex queries, which allows for sophisticated data retrieval and manipulation. This is particularly useful for handling diverse queries in a library management system, such as searching for books, managing user records, and tracking borrowing histories.

2. **ACID Compliance:**
   PostgreSQL ensures the integrity and reliability of data through ACID (Atomicity, Consistency, Isolation, Durability) compliance. This guarantees that transactions are processed reliably, which is crucial for maintaining accurate records of book checkouts, returns, and user interactions.

3. **Extensible and Flexible:**
   PostgreSQL is highly extensible, allowing the addition of custom functions and data types as needed. This flexibility supports the evolving needs of a library management system, such as integrating new features or adapting to changing requirements without compromising performance.

4. **Strong Community and Support:**
   PostgreSQL has a vibrant and active community, offering extensive documentation, resources, and support. This community-driven development ensures that PostgreSQL remains up-to-date with the latest features and security patches, providing a reliable and well-supported database solution.

5. **Data Integrity and Referential Integrity:**
   With its support for foreign keys, constraints, and transactions, PostgreSQL helps maintain data integrity and enforce referential integrity. This is essential for managing complex relationships between books, borrowers, and transactions in a library system.

6. **Performance and Scalability:**
   PostgreSQL is known for its performance optimization features, including indexing, query optimization, and support for large datasets. As your library grows, PostgreSQL can scale to handle increased load and data volume efficiently.

7. **Support for JSON and NoSQL Features:**
   PostgreSQL supports JSON data types and functions, allowing for flexibility in handling semi-structured data. This can be advantageous if your library management system needs to store and query non-relational data alongside traditional relational data.

Table Specification:

# Database Schema: `library_core`

## Tables and Relationships

### 1. `book_details`
**Introduction:** This table stores detailed information about each book, including its ISBN, author, and title. Each book has a unique identifier (`id`).

- **Primary Key (PK):** `id`
- **Columns:**
  - `id`: `bigint` (PK, auto-generated)
  - `create_date`: `timestamp(6)`
  - `last_update`: `timestamp(6)`
  - `version`: `bigint` (not null)
  - `isbn`: `varchar(25)` (not null)
  - `author`: `varchar(255)` (not null)
  - `title`: `varchar(255)` (not null)

### 2. `book`
**Introduction:** This table represents the instances of books in the library. It includes a reference to the `book_details` table and tracks whether each book is borrowed.

- **Primary Key (PK):** `id`
- **Foreign Key (FK):** `book_details_id` references `book_details(id)`
- **Columns:**
  - `id`: `bigint` (PK, auto-generated)
  - `is_borrowed`: `boolean` (not null)
  - `book_details_id`: `bigint` (FK)
  - `create_date`: `timestamp(6)`
  - `last_update`: `timestamp(6)`
  - `version`: `bigint` (not null)

### 3. `borrower`
**Introduction:** This table contains information about individuals who borrow books from the library, including their name and email address.

- **Primary Key (PK):** `id`
- **Columns:**
  - `id`: `bigint` (PK, auto-generated)
  - `create_date`: `timestamp(6)`
  - `last_update`: `timestamp(6)`
  - `version`: `bigint` (not null)
  - `email`: `varchar(255)` (not null)
  - `name`: `varchar(255)` (not null)

### 4. `borrow_details`
**Introduction:** This table tracks the borrowing activities, linking books to borrowers and recording the dates of borrowing and returning.

- **Primary Key (PK):** `id`
- **Foreign Keys (FK):**
  - `book_id` references `book(id)`
  - `borrower_id` references `borrower(id)`
- **Columns:**
  - `id`: `bigint` (PK, auto-generated)
  - `book_id`: `bigint` (FK)
  - `borrow_date`: `timestamp(6)` (not null)
  - `borrower_id`: `bigint` (FK)
  - `create_date`: `timestamp(6)`
  - `last_update`: `timestamp(6)`
  - `return_date`: `timestamp(6)`
  - `version`: `bigint` (not null)

### 5. `group_details`
**Introduction:** This table defines various groups within the library system, which can be used for categorizing users or roles.

- **Primary Key (PK):** `id`
- **Columns:**
  - `id`: `bigint` (PK, auto-generated)
  - `create_date`: `timestamp(6)`
  - `last_update`: `timestamp(6)`
  - `version`: `bigint` (not null)
  - `name`: `varchar(255)`

### 6. `role`
**Introduction:** This table defines roles within the library system, which can be assigned to users or groups to manage permissions.

- **Primary Key (PK):** `id`
- **Columns:**
  - `id`: `bigint` (PK, auto-generated)
  - `create_date`: `timestamp(6)`
  - `last_update`: `timestamp(6)`
  - `version`: `bigint` (not null)
  - `name`: `varchar(255)` (not null, unique)

### 7. `group_roles`
**Introduction:** This table establishes the relationship between groups and roles, defining which roles are assigned to which groups.

- **Primary Key (PK):** `group_id`, `role_id`
- **Foreign Keys (FK):**
  - `group_id` references `group_details(id)`
  - `role_id` references `role(id)`
- **Columns:**
  - `group_id`: `bigint` (FK)
  - `role_id`: `bigint` (FK)

### 8. `system_user`
**Introduction:** This table stores information about users of the system, including their authentication details, status, and personal information.

- **Primary Key (PK):** `id`
- **Columns:**
  - `id`: `bigint` (PK, auto-generated)
  - `is2fa_enabled`: `boolean` (not null)
  - `status`: `smallint` (check constraint: 0 <= status <= 4)
  - `create_date`: `timestamp(6)`
  - `last_update`: `timestamp(6)`
  - `version`: `bigint` (not null)
  - `auth_pin`: `varchar(255)`
  - `email`: `varchar(255)`
  - `first_name`: `varchar(255)`
  - `last_name`: `varchar(255)`
  - `password`: `varchar(255)`

### 9. `user_groups`
**Introduction:** This table maps users to groups, allowing for the management of group memberships within the system.

- **Primary Key (PK):** `group_id`, `user_id`
- **Foreign Keys (FK):**
  - `group_id` references `group_details(id)`
  - `user_id` references `system_user(id)`
- **Columns:**
  - `group_id`: `bigint` (FK)
  - `user_id`: `bigint` (FK)

Please reffer the bellow image for for more understanding
![image](doc%2Fdb-design.png) 

## Unit tests and Code Coverage
The project includes unit tests written with JUnit. You can run these tests to verify the functionality of the API.

## CI/CD

The Continuous Integration and Continuous Deployment (CI/CD) pipeline for the Library Management System is set up using GitHub Actions. The workflow is designed to automate the process of building and deploying Docker images to Docker Hub. Here’s how it works:

- **GitHub Actions Workflow:**
  The CI/CD pipeline is configured to automatically compile, build, and push Docker images to Docker Hub whenever a new release is created. The workflow file that defines this process is located in the  [github/workflows/main.yml](.github%2Fworkflows%2Fmain.yml) directory of the repository.

- **Docker Hub Integration:**
  After a new release is made, the workflow builds the Docker image and pushes it to Docker Hub. You can access and pull the latest Docker image using the following URL:

  [docker pull dasunkad/library-manager-core:0.0.4](https://hub.docker.com/repository/docker/dasunkad/library-manager-core/tags)

- **Updating Docker Compose:**
  When a new release with a new Docker image tag is created, make sure to update the `docker-compose.yml` file to reference the latest image tag. This ensures that the Docker Compose setup uses the most recent version of the application when run the application locally.

By following these steps, the application deployment is streamlined, and you can easily manage and deploy updates with minimal manual intervention.

