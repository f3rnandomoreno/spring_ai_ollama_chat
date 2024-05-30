# Spring AI Ollama Chat

This project is an example of a Retrieval-Augmented Generation (RAG) application using Ollama and Spring AI. It employs ETL (Extract, Transform, Load) processes to load a PDF into a VectorStore (ChromaDB) with Spring AI, allowing for question-and-answer interactions based on the content of the PDF.

## Prerequisites

- JDK 21 or higher
- Maven 3.6.0 or higher
- Docker
- Ollama

Start the required services with Docker Compose (ChromaDB):
  ```bash
  cd development
  docker-compose up -d
  ```

## Endpoint

### `POST /chat/ask`

This endpoint receives a question in the request body and returns an answer generated related with the pdf included in the project.

#### Request

- **URL:** `/chat/ask`
- **HTTP Method:** `POST`
- **Request Body:** A plain text containing the question.

#### Example Request

```bash
curl -X POST http://localhost:8080/chat/ask -d 'What Andrew says about MATLAB?'
