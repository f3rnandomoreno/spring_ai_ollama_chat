This project is a RAG example with Ollama and Spring AI. It uses ETL (Extract, Transform and Load) to load a PDF with spring ai in a VectorStore (ChromaDB).

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

## Endpoints

### `POST /chat/ask`

This endpoint receives a question in the request body and returns an answer generated related with the pdf included in the project.

#### Request

- **URL:** `/chat/ask`
- **HTTP Method:** `POST`
- **Request Body:** A plain text containing the question.

#### Example Request

```bash
curl -X POST http://localhost:8080/chat/ask -d 'What Andrew says about MATLAB?'
