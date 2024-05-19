package com.ciklum.chatollamaspring.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.StreamingChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.transformer.ContentFormatTransformer;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/** Implement ETL (Extract, Transform and Load) structure to do a RAG */
@Service
@AllArgsConstructor
@Log4j2
public class RAGService {
  // Extract
  final DocumentReader documentReader;
  // Transformer
  final TextSplitter textSplitter;
  final ContentFormatTransformer contentFormatTransformer;
//  final KeywordMetadataEnricher keywordMetadataEnricher; it takes time to do it
  // Load
  final VectorStore vectorStore;

  // Chat
  final ChatClient chatClient;
  final StreamingChatClient streamingChatClient;

  public Flux<String> askStream(String question, boolean withContext) {
    if (!withContext) {
      return streamingChatClient.stream(question);
    }
    return streamingChatClient.stream(getPromptWithContext(question).getContents());
  }

  /** Load a PDF file from the filepath and take the text from it */
  @PostConstruct
  public void loadPDF() {
    vectorStore.accept(
        contentFormatTransformer.apply(
            textSplitter.apply(documentReader.get())));
  }

  public String ask(String question, boolean withContext) {
    if (!withContext) {
      return chatClient.call(question);
    }
    var questionWithContext = getPromptWithContext(question);
    return chatClient.call(questionWithContext.getContents());
  }

  // it just takes one document to get the context because this computer is so slow with ollama
  private Prompt getPromptWithContext(String question) {
    log.traceEntry("Ask question {}", question);
    PromptTemplate promptTemplate =
        new PromptTemplate(
            "You are assistant. "
                + "You answer shortly the question:\\n\"\"\"<question>{question}</question>\"\"\"\n about this information:\\n\"\"\"<information>{document}</information>\"\"\"");
    List<Document> relatedDocuments = vectorStore.similaritySearch(question);
    log.info("Nearest distance {}", relatedDocuments.get(0).getMetadata().get("distance"));
    log.info("Nearest document {}", relatedDocuments.get(0).getContent().substring(20));
    return promptTemplate.create(
        Map.of(
            "question",
            question,
            "document",
            relatedDocuments.stream().map(Document::getContent).findFirst().orElseThrow()));
  }
}
