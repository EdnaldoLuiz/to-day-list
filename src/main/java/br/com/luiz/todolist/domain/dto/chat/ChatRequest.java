package br.com.luiz.todolist.domain.dto.chat;

import java.util.ArrayList;
import java.util.List;

import br.com.luiz.todolist.domain.model.MessageModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRequest {

    private String model;
    private List<MessageModel> messages;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        messages = new ArrayList<>();
        messages.add(new MessageModel("user", prompt));
    }
}