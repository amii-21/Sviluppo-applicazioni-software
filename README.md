# Catering Management System

A management system for a catering company, developed as a full software engineering exercise from requirements gathering to implementation and testing.

## What this project covers

The assignment followed a complete development process:

1. **Interviews with event organisers** — requirements elicitation and business rules definition
2. **Domain model and contracts** — formal specification of the system's entities and operations
3. **Database design** — relational schema and SQL implementation
4. **Layered implementation** — business logic / persistence separation using a custom `PersistenceManager` with plain JDBC (no ORM)
5. **JUnit 5 test suite** — scenarios covering holiday management, staff availability, and employee statistics

## Stack

- Java
- JDBC (pure, no Hibernate)
- MySQL
- Maven
- JUnit 5

## How to run

```bash
# Prerequisites: Java 17+, MySQL running locally

# 1. Clone the repo
git clone https://github.com/amii-21/Sviluppo-applicazioni-software.git

# 2. Create the database
mysql -u root -p < src/main/resources/schema.sql

# 3. Configure DB connection
# Edit src/main/resources/database.properties with your credentials

# 4. Build and run
mvn clean install
mvn exec:java
```

## Project structure

```
src/
├── main/
│   ├── java/
│   │   ├── business/     # Business logic layer
│   │   └── persistence/  # PersistenceManager and DAO classes
│   └── resources/
│       └── schema.sql
└── test/
    └── java/             # JUnit 5 test cases
```

---
