package br.com.luiz.todolist.domain.dto.chat;

import java.util.List;

import br.com.luiz.todolist.domain.model.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    
    private List<Choice> choices;

    @Data
    public static class Choice {
        private MessageModel message;
        private int index;
    }
}
