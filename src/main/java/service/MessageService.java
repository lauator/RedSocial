package service;

import repository.MessageRepository;

public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void createMessage(String username, String message, String date){

    }

}
