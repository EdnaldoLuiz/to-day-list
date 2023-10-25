package br.com.luiz.todolist.domain.controller;

import br.com.luiz.todolist.domain.model.MessageModel;

public class InvalidChatMessageException extends RuntimeException {

    public InvalidChatMessageException(MessageModel messageModel) {
        
    }

}
