package br.com.luiz.todolist.infra.service;

import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    public String getPrompt(String question) {
        String prompt = "essa pergunta '" + question + "' nao pode ser respondida";
        return prompt;
    }
}