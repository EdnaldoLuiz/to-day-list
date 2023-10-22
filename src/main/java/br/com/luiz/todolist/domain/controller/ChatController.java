package br.com.luiz.todolist.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.luiz.todolist.domain.dto.chat.ChatRequestData;
import br.com.luiz.todolist.domain.dto.chat.ChatResponseData;
import br.com.luiz.todolist.domain.model.MessageModel;
import br.com.luiz.todolist.infra.service.ChatService;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})    
public class ChatController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<MessageModel> chat(@RequestBody String prompt) {
        try {
            ChatRequestData requestDTO = new ChatRequestData(model, chatService.getPrompt(prompt));
            ChatResponseData responseDTO = restTemplate.postForObject(apiUrl, requestDTO, ChatResponseData.class);
            if (requestDTO == null || responseDTO.getChoices() == null || responseDTO.getChoices().isEmpty())
                return ResponseEntity.badRequest().body(new MessageModel("user", "erro na consulta"));
            return ResponseEntity.ok(responseDTO.getChoices().get(0).getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
