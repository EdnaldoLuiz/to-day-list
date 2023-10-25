package br.com.luiz.todolist.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import br.com.luiz.todolist.infra.exception.InvalidChatMessageException;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.OPTIONS })
public class ChatController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @PostMapping("/chat")
    public ResponseEntity<MessageModel> chat(@RequestBody String prompt) {
        ChatRequestData requestDTO = new ChatRequestData(model, ChatRequestData.getPrompt(prompt));
        ChatResponseData responseDTO = restTemplate.postForObject(apiUrl, requestDTO, ChatResponseData.class);
        boolean invalid = requestDTO == null || responseDTO.getChoices() == null || responseDTO.getChoices().isEmpty();

        if (invalid) {
            throw new InvalidChatMessageException(new MessageModel("user", "erro na consulta"));
        }
        return ResponseEntity.ok(responseDTO.getChoices().get(0).getMessage());
    }
}
