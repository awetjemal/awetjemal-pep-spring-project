package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id){
        Optional<Message> optional = messageRepository.findById(id) ;
        if(optional.isPresent())
            return optional.get();
        //Message returnedMessage = 
       
        return null;
    }

    public Integer deleteMessageById(int id){
        List<Message> messageList = messageRepository.findAll();
        if(messageList.contains(getMessageById(id))){
            int countBeforeDelete = messageRepository.findAll().size();

            messageRepository.deleteById(id);

            int countAfterDelete = messageRepository.findAll().size();
            int rowsUpdated = countBeforeDelete - countAfterDelete;
            System.out.println("method-deleteMessageById()  updated-rows   " + rowsUpdated);
            if(rowsUpdated > 0)
                return rowsUpdated;
        }
        
        
        return null;
    }

    public Integer updateMessageById(int id, String text){
        List<Message> messageList = messageRepository.findAll();
        Message messageTobeUpdated = getMessageById(id);
        if(text.equals(""))
            return null;
        if(messageList.contains(messageTobeUpdated) &&
             text.length() < 256){
            
             System.out.println("method----messageTobeUpdated.getMessageText().equals(text)---" + messageTobeUpdated.getMessageText() + "  " + text);
            if(messageTobeUpdated.getMessageText() != text){
                messageTobeUpdated.setMessageText(text);
                messageRepository.save(messageTobeUpdated);
                return 1;
            }
        }
               
        return null;
    }

    public List<Message> getAllMessagesByUser(int id){
        List<Message> userMessages = new ArrayList<>();
        List<Message> allMessages = messageRepository.findAll();
        for(Message m: allMessages){
            if(m.getPostedBy() == id)
                userMessages.add(m);
        }

        return userMessages;
    }

}
