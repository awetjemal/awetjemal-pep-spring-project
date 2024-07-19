package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountService accountService;

    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public Message addMessage(Message message){
        
        if(message.getMessageText() != "" &&
        message.getMessageText().length() < 256 &&
        accountService.userExist(message.getPostedBy())){
            return messageRepository.save(message);
        }
        return null;
    }
}
