package com.ciklum.chatollamaspring.controller;

import com.ciklum.chatollamaspring.service.RAGService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fernando Moreno Ruiz
 * */
@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {

  final RAGService ragService;

  @PostMapping("/ask")
  public String ask(@RequestBody String question) {
    return ragService.ask(question,true);
  }
}
