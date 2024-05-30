package com.ciklum.chatollamaspring.service;

import static org.junit.jupiter.api.Assertions.*;

import com.ciklum.chatollamaspring.ComponentTestBase;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

/**
 * @author Fernando Moreno Ruiz
 */
// TODO pending evaluation of every test because BasicEvaluationTest of spring ai is TBD yet.
class RAGServiceComponentTest extends ComponentTestBase {

  private static final Logger log = LoggerFactory.getLogger(RAGServiceComponentTest.class);
  @Autowired VectorStore vectorStore;

  @Autowired RAGService ragService;
  @Autowired ChatModel chatModel;

  @Test
  void testAskStreamingWithContext() {
    String question = "What Andrew says about MATLAB?";
    log.info(question);
    var responseFlux = ragService.askStream(question, true);
    StepVerifier.create(responseFlux)
        .thenConsumeWhile(
            response -> {
              System.out.print(response);
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testAskStreamingWithoutContext() {
    String question = "What Andrew says about MATLAB?";
    log.info(question);
    var responseFlux = ragService.askStream(question, false);
    StepVerifier.create(responseFlux)
        .thenConsumeWhile(
            response -> {
              System.out.print(response);
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testAskWithoutContext() {
    String question = "What Andrew says about MATLAB?";
    log.info(question);
    var response = ragService.ask(question, false);
    log.info("Response: " + response);
    assertNotNull(response);
  }

  @Test
  void testAskWithContext() {
    String question = "What Andrew says about MATLAB?";
    log.info(question);
    var response = ragService.ask(question, true);
    log.info("Response: " + response);
    assertNotNull(response);
  }

  @Test
  void testCompareAskWithoutContext() {
    String question = "What Andrew says about MATLAB?";
    log.info(question);
    var responseWithoutContext = ragService.ask(question, false);
    log.info("Response WITHOUT context: " + responseWithoutContext);
    var responseWithContact = ragService.ask(question, true);
    log.info("Response WITH context: " + responseWithContact);
  }


}
