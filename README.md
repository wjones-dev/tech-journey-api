# Tech Journey API

Spring Boot REST API powering the Tech Journey interactive technology museum, API Explorer, and Engineering Lab.

The backend provides timeline data, a temporary CRUD sandbox, and interactive engineering experiments demonstrating Java, Spring Boot, and Spring Security concepts.

The API is designed to support the frontend experience while also functioning as an explorable engineering project on its own.

---

## Live Application

**Tech Journey:**  
https://techjourney.dev

**Museum / Engineering Experience:**  
https://museum.techjourney.dev

---

# Overview

Tech Journey uses a separate frontend and backend architecture:

```text
Angular Frontend
      ↓
    /api
      ↓
Nginx Reverse Proxy
      ↓
Spring Boot REST API
      ↓
Spring Data JPA
      ↓
H2 Database
```

The backend is responsible for:

- timeline data
- REST API endpoints
- CRUD sandbox operations
- Java Stream experiments
- Spring Security demonstrations
- request validation
- centralized API error handling
- persistence

---

# Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Backend language |
| Spring Boot 4 | Application framework |
| Spring Web | REST API |
| Spring Data JPA | Persistence abstraction |
| Hibernate | ORM |
| Spring Security | Authentication and authorization experiments |
| H2 | Application database |
| Maven | Build and dependency management |
| Docker | Production containerization |

---

# API Structure

The API is organized into three primary areas:

```text
/api
│
├── /timeline
│      Museum timeline data
│
├── /sandbox/events
│      Temporary CRUD playground
│
└── /lab
       Engineering Lab experiments
```

---

# Timeline API

The Timeline API provides the technology data displayed throughout the Museum.

## Get All Timeline Events

```http
GET /api/timeline
```

Example response:

```json
[
  {
    "id": 1,
    "year": 1983,
    "category": "GAMING",
    "title": "Atari 2600",
    "description": "A defining moment in early home gaming.",
    "technology": "Atari"
  }
]
```

---

## Get Timeline Event by ID

```http
GET /api/timeline/{id}
```

Example:

```http
GET /api/timeline/1
```

If the requested timeline event does not exist, the API returns:

```http
404 Not Found
```

with a structured error response.

---

# Sandbox API

The Sandbox API provides a temporary environment for experimenting with standard REST CRUD operations.

Sandbox records are separate from the curated Museum timeline.

```text
CREATE
READ
UPDATE
DELETE
```

Temporary sandbox data is automatically removed after its retention period.

---

## Get All Sandbox Events

```http
GET /api/sandbox/events
```

---

## Get Sandbox Event

```http
GET /api/sandbox/events/{id}
```

---

## Create Sandbox Event

```http
POST /api/sandbox/events
```

Example request:

```json
{
  "year": 2026,
  "category": "AI",
  "title": "AI Engineering",
  "description": "Experimenting with modern AI systems.",
  "technology": "Artificial Intelligence"
}
```

---

## Update Sandbox Event

```http
PUT /api/sandbox/events/{id}
```

---

## Delete Sandbox Event

```http
DELETE /api/sandbox/events/{id}
```

The Sandbox API is used by the Angular API Explorer to demonstrate how frontend applications communicate with REST services.

---

# Engineering Lab API

The Engineering Lab exposes backend behavior through controlled interactive experiments.

The lab follows the overall Tech Journey philosophy:

> **PLAY → UNDERSTAND → ENGINEER**

Rather than simply describing Java and Spring concepts, the frontend sends real requests to this API and visualizes what happens.

---

# Java Data Processing Experiment

## Collections • Streams • Lambdas

```http
POST /api/lab/java/streams/run
```

The experiment runs a configurable Java Stream pipeline against timeline data.

Example request:

```json
{
  "category": "AI",
  "sortDirection": "DESC",
  "resultType": "TITLE",
  "limit": 5
}
```

Supported categories include:

```text
ALL
GAMING
MUSIC
COMPUTING
INTERNET
DEVELOPMENT
CLOUD
AI
```

Sort directions:

```text
ASC
DESC
```

Result types:

```text
FULL_OBJECT
TITLE
YEAR
```

---

## Processing Pipeline

Depending on the selected options, the backend builds a Java Stream pipeline similar to:

```text
COLLECTION
    ↓
STREAM
    ↓
FILTER
    ↓
SORT
    ↓
MAP
    ↓
LIMIT
    ↓
TO_LIST
    ↓
RESULT
```

Example response:

```json
{
  "originalCount": 16,
  "filteredCount": 2,
  "finalCount": 2,
  "executionTimeMs": 4,
  "stages": [
    "COLLECTION",
    "STREAM",
    "FILTER",
    "SORT",
    "MAP",
    "TO_LIST",
    "RESULT"
  ],
  "results": [
    "AI Agents / MCP / Tool Use / Coding Agents",
    "Generative AI"
  ]
}
```

The response allows the Angular frontend to visualize each processing stage.

---

## Validation

Invalid requests return:

```http
400 Bad Request
```

Examples include:

```text
Unknown category
Invalid sort direction
Invalid result type
limit <= 0
```

---

# Spring Security Experiment

The Spring Security lab demonstrates the difference between:

> **Authentication — Who are you?**

and

> **Authorization — What are you allowed to do?**

The experiment uses a controlled demo account rather than a full user-management system.

---

## Authentication Scenarios

### Valid Credentials

A successful login authenticates the demo user.

```text
Credentials
    ↓
Security Filter Chain
    ↓
Authentication
    ↓
User Verification
    ↓
Security Context
    ↓
Access Granted
```

---

### Invalid Password

Invalid credentials return:

```http
401 Unauthorized
```

---

## User Endpoint

```http
GET /api/lab/security/user
```

A successfully authenticated user with the required role receives:

```http
200 OK
```

---

## Admin Endpoint

```http
GET /api/lab/security/admin
```

A standard USER attempting to access an ADMIN resource receives:

```http
403 Forbidden
```

This allows the frontend experiment to demonstrate that authentication and authorization are separate concerns.

---

# Error Handling

The application uses centralized exception handling to provide consistent API responses.

Typical status codes include:

| Status | Meaning |
|---|---|
| `200` | Request succeeded |
| `201` | Resource created |
| `400` | Invalid request |
| `401` | Authentication required or failed |
| `403` | Authenticated but not authorized |
| `404` | Resource not found |
| `500` | Unexpected server error |

Errors are returned using a structured API error response rather than raw framework exception output.

---

# Data Model

A timeline event contains:

```text
id
year
category
title
description
technology
```

Supported timeline categories include:

```text
GAMING
MUSIC
COMPUTING
INTERNET
DEVELOPMENT
CLOUD
AI
```

---

# Persistence

Spring Data JPA provides the persistence layer.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
H2
```

H2 is currently used because Tech Journey does not require an external production database for its curated timeline and demonstration workloads.

---

# Project Structure

The backend follows a layered architecture:

```text
src/main/java
│
├── controller
│
├── service
│
├── repository
│
├── model
│
├── dto
│
├── exception
│
└── configuration
```

Responsibilities are separated between HTTP handling, business logic, persistence, domain models, and API contracts.

---

# Local Development

## Prerequisites

```text
Java 17+
Maven 3.8+
```

---

## Clone the Repository

```bash
git clone https://github.com/wjones-dev/tech-journey-api.git

cd tech-journey-api
```

---

## Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

---

# Build

Create the application JAR:

```bash
mvn clean package
```

The packaged application will be generated under:

```text
target/
```

Run it with:

```bash
java -jar target/*.jar
```

---

# H2 Console

When enabled for local development, the H2 console is available at:

```text
http://localhost:8080/h2-console
```

The H2 console is intended for development and inspection rather than general application access.

---

# Docker

The production API runs inside a Docker container using a Java 17 runtime.

Build the image:

```bash
docker build -t tech-journey-api .
```

Run locally:

```bash
docker run -p 8080:8080 tech-journey-api
```

---

# Production Deployment

The API is deployed to DigitalOcean alongside the Angular frontend.

```text
Internet
   ↓
museum.techjourney.dev
   ↓
Nginx
   │
   ├── Angular Frontend
   │
   └── /api
         ↓
      Spring Boot
```

The frontend and backend run as separate Docker services.

Nginx acts as the public entry point and proxies `/api` requests to the Spring Boot container.

---

# Related Repositories

## Angular Frontend

https://github.com/wjones-dev/tech-journey

Contains the Museum, Technology Detail views, API Explorer, Engineering Lab, and interactive frontend experiments.

## Deployment

https://github.com/wjones-dev/tech-journey-deployment

Contains the Docker Compose configuration used to deploy the frontend and API services together.

---

# Project Goal

Tech Journey is designed to demonstrate engineering through interaction rather than through a list of technologies.

The backend exists not only to provide data to the frontend, but also to expose real Java and Spring behavior that visitors can inspect through the API Explorer and Engineering Lab.

Instead of saying:

> Java • Spring Boot • REST • JPA • Spring Security

the project provides working examples of those technologies communicating as part of a deployed application.

> **Build it. Run it. Understand it.**
