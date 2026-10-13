# 🎬 Blockbuster API – Movie Rental Service

A RESTful backend for a movie rental store, built with **Java**, **Spring Boot**, **Spring Data JPA** and an **H2** database. It manages a catalogue of movies, a customer base with addresses, and customer ratings for movies, and exposes analytics such as top‑rated movies and customer counts per city.

---

## ✨ Features

- **Movies** – full CRUD, search by title, filter by release‑year range, genre enum, rental flag
- **Customers** – full CRUD, unique email, auto‑set registration date, one‑to‑one address
- **Customer search** – by email, name (first or last, case‑insensitive), city, or registration date
- **Ratings** – customers rate movies from 1 to 10
- **Analytics** – top movies by average rating (paginated), average rating per movie, most‑rated movies, customer count per city
- **Sample data** – movies, customers and random ratings are seeded on startup
- **H2 console** – browse the database in the browser
- **Validation & error handling** – Jakarta Bean Validation annotations and a global exception handler

---

## 🛠 Tech Stack

| Layer          | Technology                                   |
| -------------- | -------------------------------------------- |
| Language       | Java 17+                                     |
| Framework      | Spring Boot 3 (Web, Data JPA, Validation)    |
| ORM            | Hibernate / Jakarta Persistence              |
| Database       | H2 (file‑based)                              |
| Boilerplate    | Lombok                                       |
| Serialization  | Jackson                                      |
| Testing        | JUnit 5, Spring Boot Test                    |

---

## 📁 Project Structure

```
.
├── main
│   ├── java/com/example/blockbusterapiv3
│   │   ├── BlockbusterApiV3Application.java   # Entry point
│   │   ├── controller/                        # REST endpoints
│   │   │   ├── MovieController.java
│   │   │   ├── CustomerController.java
│   │   │   └── RatingController.java
│   │   ├── service/                           # Business logic
│   │   │   ├── MovieService.java
│   │   │   ├── CustomerService.java
│   │   │   └── RatingService.java
│   │   ├── repository/                        # Spring Data JPA repositories
│   │   │   ├── MovieRepository.java
│   │   │   ├── CustomerRepository.java
│   │   │   └── RatingRepository.java
│   │   ├── model/                             # JPA entities
│   │   │   ├── Movie.java
│   │   │   ├── MovieGenre.java
│   │   │   ├── Customer.java
│   │   │   ├── Address.java
│   │   │   └── Rating.java
│   │   ├── exception/                         # Custom exceptions & handler
│   │   │   ├── MovieNotFoundException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   └── utility/
│   │       └── DataLoader.java                # Seeds sample data on startup
│   └── resources
│       └── application.properties
└── test
    └── java/com/example/blockbusterapiv3
        └── BlockbusterApiV3ApplicationTests.java
```

The code follows a classic layered architecture:

```
Controller  →  Service  →  Repository  →  H2 Database
```

---

## 🗂 Data Model

```
┌──────────────┐ 1      1 ┌──────────────┐
│   Customer   │──────────│   Address    │
│──────────────│          │──────────────│
│ id           │          │ id           │
│ first_name   │          │ city         │
│ last_name    │          │ zip_code     │
│ email (uniq) │          └──────────────┘
│ registration │
│   Date       │
└──────┬───────┘
       │ 1
       │
       │ *
┌──────┴───────┐ *      1 ┌──────────────┐
│    Rating    │──────────│    Movie     │
│──────────────│          │──────────────│
│ id           │          │ id           │
│ rating 1–10  │          │ title        │
└──────────────┘          │ releaseYear  │
                          │ genre        │
                          │ isRented     │
                          └──────────────┘
```

- **Movie** – `title` (required), `releaseYear` (1888–2025), `genre`, `isRented` (defaults to `false`)
- **MovieGenre** – `DRAMA`, `COMEDY`, `ACTION`, `THRILLER`, `HORROR`, `ROMANCE`, `SCI_FI`
- **Customer** – `first_name`, `last_name`, unique valid `email`, `registrationDate` (set automatically on creation)
- **Address** – `city`, `zip_code`; saved and deleted together with its customer (cascade)
- **Rating** – integer from 1 to 10, linked to one movie and one customer

---

## 🚀 Getting Started

### Prerequisites

- JDK 17 or newer
- Maven 3.9+ (or the Maven wrapper)

### Setup

> **Note:** This repository currently contains only the source folders (`main/` and `test/`). It has no `pom.xml`, and the folders are not under `src/`. To build it, place them in a standard Spring Boot project layout and add a `pom.xml`.

1. Generate a Spring Boot project at [start.spring.io](https://start.spring.io) with:
   - Group `com.example`, Artifact `blockbuster-api-v3`
   - Dependencies: **Spring Web**, **Spring Data JPA**, **Validation**, **H2 Database**, **Lombok**
2. Copy this repo's `main/` and `test/` folders into the generated project's `src/` folder.
3. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

The API starts on **http://localhost:8080**.

### Run tests

```bash
./mvnw test
```

---

## ⚙️ Configuration

`main/resources/application.properties`:

| Property                         | Value                              |
| -------------------------------- | ---------------------------------- |
| `spring.datasource.url`          | `jdbc:h2:file:./data/blockbusterdb` |
| `spring.datasource.username`     | `sa`                               |
| `spring.datasource.password`     | *(empty)*                          |
| `spring.h2.console.enabled`      | `true`                             |
| `spring.h2.console.path`         | `/h2-console`                      |
| `spring.jpa.hibernate.ddl-auto`  | `create`                           |

### H2 Console

Open **http://localhost:8080/h2-console** and log in with:

- **JDBC URL:** `jdbc:h2:file:./data/blockbusterdb`
- **User:** `sa`
- **Password:** *(leave empty)*

> `ddl-auto=create` drops and recreates the schema on every start, so data does not persist between runs. Change it to `update` to keep your data.

### Sample data

On startup, `DataLoader` seeds:

- **10 movies** – e.g. *The Shawshank Redemption*, *The Godfather*, *The Dark Knight*, *Inception*, *Interstellar*
- **4 customers** – John Doe (Colombo), Jane Smith (Kandy), Alice Johnson (Galle), Bob Brown (Colombo)
- **Random ratings** – each customer rates 3–5 random movies with a score from 1 to 10

---

## 📡 API Reference

Base URL: `http://localhost:8080`

### Movies – `/api/movies`

| Method | Endpoint                                         | Description                       |
| ------ | ------------------------------------------------ | --------------------------------- |
| GET    | `/api/movies`                                    | List all movies                   |
| GET    | `/api/movies/{id}`                               | Get a movie by ID                 |
| GET    | `/api/movies/search?title={text}`                | Search by title (case‑insensitive) |
| GET    | `/api/movies/range?startYear={y1}&endYear={y2}`  | Movies released between two years |
| POST   | `/api/movies`                                    | Add a movie                       |
| PUT    | `/api/movies/{id}`                               | Update title, year and genre      |
| DELETE | `/api/movies/{id}`                               | Delete a movie                    |

**Example – add a movie**

```bash
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{"title": "Parasite", "releaseYear": 2019, "genre": "THRILLER"}'
```

### Customers – `/api/customers`

| Method | Endpoint                                                          | Description                          |
| ------ | ----------------------------------------------------------------- | ------------------------------------ |
| GET    | `/api/customers`                                                  | List all customers                   |
| GET    | `/api/customers/{id}`                                             | Get a customer by ID                 |
| POST   | `/api/customers`                                                  | Create a customer                    |
| PUT    | `/api/customers/{id}`                                             | Update a customer                    |
| DELETE | `/api/customers/{id}`                                             | Delete a customer (and their address) |
| GET    | `/api/customers/search?email={email}`                             | Find by email                        |
| GET    | `/api/customers/search?name={text}`                               | Find by first or last name           |
| GET    | `/api/customers/search?city={city}`                               | Find by city                         |
| GET    | `/api/customers/search/registration-date?start={date}&end={date}` | Registered between two dates         |
| GET    | `/api/customers/search/registered-after?date={date}`              | Registered after a date              |
| GET    | `/api/customers/count/city`                                       | Customer count per city              |

Dates use ISO format: `YYYY-MM-DD`. The `/search` endpoint uses only the first parameter it finds, in the order email → name → city, and returns `400` if none is given.

**Example – create a customer**

```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
        "first_name": "Nimal",
        "last_name": "Perera",
        "email": "nimal.perera@example.com",
        "address": { "city": "Negombo", "zip_code": "11500" }
      }'
```

**Example – customer count per city**

```bash
curl http://localhost:8080/api/customers/count/city
# [["Colombo",2],["Kandy",1],["Galle",1]]
```

### Ratings – `/api/ratings`

| Method | Endpoint                                  | Description                                  |
| ------ | ----------------------------------------- | -------------------------------------------- |
| GET    | `/api/ratings`                            | List all ratings                             |
| GET    | `/api/ratings/top?page={n}&size={n}`      | Movies ranked by average rating (paginated)  |
| GET    | `/api/ratings/avarages`                   | Average rating for each movie                |
| GET    | `/api/ratings/most-rated`                 | Movies ordered by number of ratings          |
| POST   | `/api/ratings?customerId={id}`            | Add a rating for a customer                  |
| PUT    | `/api/ratings/{id}`                       | Update a rating                              |
| DELETE | `/api/ratings/{id}`                       | Delete a rating                              |

> The averages endpoint is spelled `/avarages` in the code.

**Example – top 5 movies**

```bash
curl "http://localhost:8080/api/ratings/top?page=0&size=5"
```

---


---


---


