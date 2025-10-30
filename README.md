# 🧩 FunPokeDex

A simple **Spring Boot 3 + Java 21** microservice that connects to the **PokeAPI** to retrieve Pokémon information and uses the **FunTranslations API** to translate descriptions into *Yoda* or *Shakespeare* style.

This project is fully containerized — no Java or Maven installation required.  
You can build and run it **entirely with Docker**.

---

## 🚀 Quick Start

### ✅ Requirements

You only need **Docker** installed on your machine.

#### 🪟 Windows / 🍎 macOS

Download and install **Docker Desktop** from:  
👉 [https://www.docker.com/get-started](https://www.docker.com/get-started)

#### 🐧 Linux (Ubuntu example)

```bash
sudo apt update
sudo apt install -y docker.io
sudo systemctl enable docker
sudo systemctl start docker
```

Verify Docker works:
```bash
docker --version
```

---

## 🛠️ Build and Run with Docker

#### 1. Clone this repository
```bash
git clone https://github.com/your-username/funpokedex.git
cd funpokedex
```

> 💡 **Note:**  
> If you don’t have `git` installed, install it with:
>
> **Windows:** [Download Git for Windows](https://git-scm.com/download/win)  
> **macOS:** Run `brew install git` (requires Homebrew)  
> **Ubuntu / Debian:**
> ```bash
> sudo apt install -y git
> ```


---

#### 2. Build the Docker image
```bash
docker build -t funpokedex:latest .
```

This will:

- Use Maven inside Docker to build the JAR file
- Package the app into a minimal Java 21 runtime image

_The first build may take a few minutes as Maven downloads dependencies._

---

#### 3. Run the container
```bash
docker run -d -p 8080:8080 --name funpokedex funpokedex:latest
```

Check that it’s running:
```bash
docker ps
```

View logs:
```bash
docker logs -f funpokedex
```

---

## 🌐 Test the API

Once the container is running, open your browser or use `curl`:

#### ▶️ Get Pokémon info
```bash
curl http://localhost:8080/pokemon/bulbasaur
```

Example response:
```json
{
  "name": "bulbasaur",
  "description": "A strange seed was planted on its back at birth.",
  "habitat": "grassland",
  "legendary": false
}
```

#### ▶️ Get translated Pokémon info
```bash
curl http://localhost:8080/pokemon/translated/bulbasaur
```

Example response:
```json
{
  "name": "bulbasaur",
  "description": "A strange seed was planted on its back at birth. Speak like Yoda you will.",
  "habitat": "grassland",
  "legendary": false
}
```

If the translation API is unavailable, the app automatically falls back to the original description.

---

## 🧱 Project Structure

```
funpokedex/
├── src/
│   ├── main/java/com/truelayer/interview/funpokedex/
│   │   ├── controller/      # REST controllers (PokemonController)
│   │   ├── service/         # Business logic (PokemonService, TranslationService)
│   │   ├── client/          # HTTP clients for PokeAPI and Translation API
│   │   ├── mapper/          # Mapper for API to domain models
│   │   └── model/           # DTOs, Enums, API response models
│   └── test/java/           # Unit tests (JUnit + Mockito)
├── pom.xml                  # Maven build configuration
├── Dockerfile               # Multi-stage Docker build
└── README.md                # This file
```

---

## 🏁 Summary

✅ Build and run with a single Docker command  
✅ No dependencies required on the host machine  
✅ REST endpoints available at `http://localhost:8080`  
✅ Graceful fallback for failed translations  
✅ Lightweight, reproducible, and production-ready container  
✅ Optional fix for missing `git` dependency in container builds

---

**Author:**  
🧑‍💻 *Sara Bellatorre*  
📧 sara.bellatorre@gmail.com  

