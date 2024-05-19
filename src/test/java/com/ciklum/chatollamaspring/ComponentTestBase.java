package com.ciklum.chatollamaspring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.chromadb.ChromaDBContainer;

import java.time.Duration;

/**
 * @author Fernando Moreno Ruiz
 * */
@SpringBootTest
public class ComponentTestBase {

  // create TestContainer with Chromadb
  static ChromaDBContainer chromadb =
      new ChromaDBContainer("chromadb/chroma")
          .withExposedPorts(8000)
          .withStartupTimeout(Duration.ofSeconds(60));


  // start the container
  static {
    chromadb.start();

  }

  @DynamicPropertySource
  static void applicationTestProperties(DynamicPropertyRegistry registry){
    registry.add(
        "spring.ai.vectorstore.chroma.client.host",
        () -> "http://"+chromadb.getHost());
    registry.add("spring.ai.vectorstore.chroma.client.port",chromadb::getFirstMappedPort);
  }
}
