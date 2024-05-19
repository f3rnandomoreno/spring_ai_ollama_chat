package com.ciklum.chatollamaspring.client;

import com.ciklum.chatollamaspring.ComponentTestBase;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.ollama.OllamaEmbeddingClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class EmbeddingClientComponentTest extends ComponentTestBase {

  @Autowired
  OllamaEmbeddingClient ollamaEmbeddingClient;

  @Autowired
  VectorStore chromaVectorStore;

  @Test
  void testCreateEmbedding() {
    var response = ollamaEmbeddingClient.call(new EmbeddingRequest(List.of("text"),null));
    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getResults()).hasSize(1);
  }


  /**
   * Test the client save a embedding and then search for similarity
   * */
  @Deprecated(forRemoval = true)
  @Test
  void testSaveAndSearchEmbedding() {
    chromaVectorStore.add(
        List.of(
            createEmbedding("I like to play basketball"),
            createEmbedding("I like the color blue"),
            createEmbedding("I am tall"),
            createEmbedding("The sky is blue")));
    var response = chromaVectorStore.similaritySearch("What sport do you like to play?");
    System.out.println(response);
    Assertions.assertThat(response).isNotNull();
  }


  private @NotNull Document createEmbedding(String text) {
    Document document = new Document(text);
    document.setEmbedding(ollamaEmbeddingClient.embed(document));
    return document;
  }
}
