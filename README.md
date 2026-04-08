Library Management System

Description

This project is a full-stack web application developed using Spring Boot to manage a library system. It allows users to perform CRUD operations (create, read, update, delete) on books through a web-based interface. The application follows a layered architecture including controller, service, repository, and model.

Technologies Used
Java 21
Spring Boot
Spring Web
Spring Data JPA
MySQL
Thymeleaf
Bootstrap
Maven

Features
View all books
Add new books
Update existing books
Delete books
Server-side rendering using Thymeleaf
Responsive UI using Bootstrap

Database

The application uses MySQL as the relational database. Tables are mapped using JPA annotations and managed automatically using Hibernate. The schema follows normalization principles and includes relationships between entities using foreign keys.

Each team member must create the database locally before running the application.

---------------------------------------------------------------------------------
Database Setup

Step 1: Create the database

Run the following SQL in MySQL:

CREATE DATABASE librarydb;
USE librarydb;

Step 2 (OPTIONAL): Insert initial categories

Run the following SQL to ensure the application has predefined categories:

INSERT INTO categories (name) VALUES
(‘Technology’),
(‘Science’),
(‘History’),
(‘Fiction’);

Setup Instructions
1.	Clone the repository

git clone https://github.com/sebasuarezt7/library-management-system
2.	Open the project

Open the project in IntelliJ IDEA (or any Java IDE) and allow Maven dependencies to load.
3.	Configure the database connection

Update the application.properties file with generic configuration:

spring.datasource.url=jdbc:mysql://localhost:3306/librarydb
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false

spring.profiles.active=local

Create a local configuration file named:

application-local.properties

Add your real database password in that file:

spring.datasource.password=your_real_password

Note: The application-local.properties file is not included in the repository and should not be committed.
4.	Run the application

Run the main class:
LibraryManagementSystemApplication.java
5.	Access the application

Open a browser and go to:
http://localhost:8080

Notes

This project demonstrates a complete Spring Boot web application with database integration, MVC architecture, and user interface design. It is intended for academic purposes. Screenshots and presentation materials are submitted separately as required.
