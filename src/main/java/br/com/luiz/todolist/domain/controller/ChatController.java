package br.com.luiz.todolist.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.luiz.todolist.domain.dto.chat.ChatRequest;
import br.com.luiz.todolist.domain.dto.chat.ChatResponse;
import br.com.luiz.todolist.domain.model.MessageModel;
import br.com.luiz.todolist.infra.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${openai.model}")
    private String model;
    
    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<MessageModel> chat(@RequestBody String prompt) {
        ChatRequest requestDTO = new ChatRequest(model, chatService.getPrompt(prompt));
        ChatResponse responseDTO = restTemplate.postForObject(apiUrl, requestDTO, ChatResponse.class);
        if(requestDTO == null || responseDTO.getChoices() == null || responseDTO.getChoices().isEmpty())
            return ResponseEntity.badRequest().body(new MessageModel("user", "erro na consulta"));
        return ResponseEntity.ok(responseDTO.getChoices().get(0).getMessage());
    }
}
