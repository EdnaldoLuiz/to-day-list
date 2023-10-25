package br.com.luiz.todolist.infra.exception;

import br.com.luiz.todolist.domain.model.MessageModel;

public class InvalidChatMessageException extends RuntimeException {

    public InvalidChatMessageException(MessageModel messageModel) {

    }

}
