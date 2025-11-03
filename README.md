# FunPokeDex

A **Spring Boot 3 + Java 21** microservice that connects to the **PokeAPI** to retrieve Pokémon information and uses the **FunTranslations API** to translate descriptions into *Yoda* or *Shakespeare* style.

This project is fully containerized — no Java or Maven installation required.
You can build and run it **entirely with Docker** or using the included Makefile.

---

## Quick Start

### Requirements

You only need **Docker** installed on your machine.

#### Windows / macOS

Download and install **Docker Desktop** from:
[https://www.docker.com/get-started](https://www.docker.com/get-started)

#### Linux (Ubuntu example)

```bash
sudo apt update
sudo apt install -y docker.io
sudo systemctl enable docker
sudo systemctl start docker
```

Verify Docker is working:

```bash
docker --version
```

---

### 1. Clone the repository

```bash
git clone https://github.com/mell0r1ne/FunPokeDex.git
cd funpokedex
```

> **Note:**
> If `git` is missing:
>
> * **Windows:** [Download Git for Windows](https://git-scm.com/download/win)
> * **macOS:** `brew install git` (requires Homebrew)
> * **Ubuntu/Debian:** `sudo apt install -y git`

---

### 2. Build and run the microservice

#### Using Makefile

> **Note:** If you don't have `make` installed:
>
> * **Linux:** `sudo apt install make`
> * **macOS:** `brew install make`
> * **Windows:** Install via [Chocolatey](https://chocolatey.org/install) with `choco install make` or use Windows Subsystem for Linux (WSL).

```bash
make start      # Build the Docker image and run the container
make logs-f     # Follow logs live
make stop       # Stop and remove container
make clean      # Remove container and image
```

#### Using Docker commands directly

```bash
docker build -t funpokedex:latest .
docker run -d -p 8080:8080 --name funpokedex funpokedex:latest
docker logs -f funpokedex
docker stop funpokedex && docker rm funpokedex
```

---

## 3. Access the API

### Swagger UI

Open your browser:

```
http://localhost:8080/swagger-ui.html
```

The Swagger UI provides:

* Interactive API testing
* Endpoint documentation with examples
* Request/response schemas
* Parameter validation rules

### OpenAPI Specification

For integration or tooling:

```bash
curl http://localhost:8080/v3/api-docs       # JSON format
curl http://localhost:8080/v3/api-docs.yaml  # YAML format
```

**Translation Logic:**

* Cave dwelling or legendary Pokémon → Yoda style
* Other Pokémon → Shakespeare style
* Fallback to original description if translation fails

---

## Makefile Reference

| Command        | Description                   |
| -------------- | ----------------------------- |
| `make start`   | Build image and run container |
| `make stop`    | Stop and remove container     |
| `make restart` | Restart container             |
| `make logs`    | Show container logs           |
| `make logs-f`  | Follow logs live              |
| `make clean`   | Remove container and image    |

> These commands simplify the Docker workflow for new users.

---

## Project Structure

```
FunPokeDex/
├── src/
│   ├── main/java/com/truelayer/interview/funpokedex/
│   │   ├── controller/              # REST API endpoints
│   │   ├── service/                 # Business logic layer
│   │   ├── client/                  # HTTP clients for external APIs
│   │   ├── mapper/                  # API response to domain mapping
│   │   ├── model/                   # DTOs, enums, and API models
│   │   ├── validator/               # Input validation
│   │   └── Main.java
│   ├── resources/
│   │   ├── application.properties
│   │   └── application-prod.properties
│   └── test                         # Unit tests 
├── target/                          # Build output
├── pom.xml
├── Dockerfile
├── Makefile
└── README.md
```

### Architecture

```
HTTP Request
    ↓
Controller (REST endpoints)
    ↓
Service (Business logic)
    ↓
Client (HTTP calls to external APIs)
    ↓
Mapper (Transform responses to DTOs)
    ↓
HTTP Response
```

---

## Summary

* Fully containerized: no host Java/Maven required
* REST endpoints at `http://localhost:8080`
* Swagger UI and OpenAPI spec included
* Lightweight, reproducible, production-ready container

---

**Author:**
*Sara Bellatorre* – [sara.bellatorre@gmail.com](mailto:sara.bellatorre@gmail.com)
