package com.ciklum.chatollamaspring.configuration;


import org.springframework.ai.document.DefaultContentFormatter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.ContentFormatTransformer;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fernando Moreno Ruiz
 * Configuration for RAG
 * */
@Configuration
public class RAGConfiguration {


  @Bean("textSplitter")
  public TextSplitter tokenTextSplitter() {
    return new TokenTextSplitter();
  }

  @Bean("pdfDocumentReader")
  public PagePdfDocumentReader pagePdfDocumentReader() {
      return new PagePdfDocumentReader("classpath:/knowledge_base/MachineLearning-Lecture01.pdf");
  }

  @Bean("contentFormatTransformer")
  public ContentFormatTransformer contentFormatTransformer() {
    return new ContentFormatTransformer(DefaultContentFormatter.builder().build());
  }

  @Bean("keywordMetadataEnricher")
  public KeywordMetadataEnricher keywordMetadataEnricher(OllamaChatModel ollamaChatClient) {
    return new KeywordMetadataEnricher(ollamaChatClient,5);
  }

}
