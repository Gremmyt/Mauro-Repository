<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Microservices Project</title>

</head>

<body>
    <h1>Microservices Project</h1>
    <p>This project allows managing a library with a microservices architecture where the Author and Book microservices communicate and make requests to each other.</p>
    <p>The project consists of the following microservices:</p>
    <ul>
        <li>
            <h3>msvc-author:</h3>
            <p>This microservice manages operations related to authors, including CRUD operations, author information retrieval, and author data management.</p>
        </li>
        <li>
            <h3>msvc-book:</h3>
            <p>This microservice handles operations related to books, such as CRUD operations, book information retrieval, and book data management.</p>
        </li>
        <li>
            <h3>msvc-config:</h3>
            <p>This microservice provides configuration management for the entire application, ensuring consistency and flexibility in configuration settings across all microservices.</p>
        </li>
        <li>
            <h3>msvc-eureka:</h3>
            <p>This microservice implements the Eureka service registry, enabling service discovery and registration for all microservices in the system.</p>
        </li>
        <li>
            <h3>msvc-gateway:</h3>
            <p>This microservice serves as the API gateway, providing a single entry point for client requests and routing requests to the appropriate microservices based on predefined rules and configurations.</p>
        </li>
    </ul>
    <p>Additionally, the project includes features such as:</p>
    <ul>
        <li>Usage of MySQL and PostgreSQL databases for data storage and management.</li>
        <li>Implementation of OpenFeign, Eureka, and Spring Cloud for building and managing microservices-based architecture.</li>
        <li>Use of DTOs to handle sensitive information.</li>
    </ul>
</body>

</html>


