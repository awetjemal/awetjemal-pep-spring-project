package com.example.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }
    /*As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. The body will contain a representation of a JSON Account, 
but will not contain an accountId.
- The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username
 does not already exist. If all these conditions are met, the response body should contain a JSON of the Account, including its accountId. 
 The response status should be 200 OK, which is the default. The new account should be persisted to the database.
- If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
- If the registration is not successful for some other reason, the response status should be 400. (Client error)
 */
    //POST localhost:8080/register
    @PostMapping(value = "/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account user){         
            if(accountService.usernameExist(user)){
                return ResponseEntity.status(409).body(null);
            }
            Account addedUser = accountService.addUser(user);
            if(addedUser != null)
                return ResponseEntity.status(200).body(addedUser);
            return ResponseEntity.status(400).body(null);
    }

    
    /*As a user, I should be able to verify my login on the endpoint POST localhost:8080/login. The request body will contain a JSON representation of an Account.
    - The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. 
    If successful, the response body should contain a JSON of the account in the response body, including its accountId. The response status should be 200 OK, 
    which is the default.
    - If the login is not successful, the response status should be 401. (Unauthorized) */
    //POST localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account user){
        Account checkedAccount = accountService.isCredentialCorrect(user);
        if(checkedAccount != null ){
            return ResponseEntity.status(200).body(checkedAccount);
        }else{
            return ResponseEntity.status(401).body(null);
        }
            
    }
    /*As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages. The request body will contain a JSON representation of a message, 
    which should be persisted to the database, but will not contain a messageId.
    - The creation of the message will be successful if and only if the messageText is not blank, is not over 255 characters, and postedBy refers to a real, existing user.
     If successful, the response body should contain a JSON of the message, including its messageId. The response status should be 200, which is the default. The new message
     should be persisted to the database.
    - If the creation of the message is not successful, the response status should be 400. (Client error) */
    @PostMapping("messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            return ResponseEntity.status(200).body(addedMessage);
        }else{
            return ResponseEntity.status(400).body(null);
        }
    }

}

