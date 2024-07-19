package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account addUser(Account user){
        if(user.getUsername() != "" && 
        user.getPassword().length() >= 4 //&&
        )
            return accountRepository.save(user);
    
        return null;
    }
    
    public boolean usernameExist(Account user){
        String name = user.getUsername();
        List<Account> allUsers = accountRepository.findAll();
        for(Account a: allUsers){
            //System.out.println("method-usernameExist " + a.getUsername());
            if(a.getUsername().equals(name))
                return true;
        }
        return false;
    }
    public boolean userExist(int id){
        
        List<Account> allUsers = accountRepository.findAll();
        for(Account a: allUsers){
            //System.out.println("method-usernameExist " + a.getUsername());
            if(a.getAccountId() == id)
                return true;
        }
        return false;
    }
    public Account isCredentialCorrect(Account user){
        String name = user.getUsername();
        String pass = user.getPassword();
        System.out.println("method-isCredentialCorrect name & pass  "+ name + "  " + pass);
        List<Account> allUsers = accountRepository.findAll();
        for(Account a: allUsers){
            //System.out.println("method-isCredentialCorrect " + a.getUsername() + "  " + a.getPassword());
            if(a.getUsername().equals(name) && a.getPassword().equals(pass))
                return a;
        }
        return null;
    }
}
