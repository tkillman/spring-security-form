package com.example.springsecurityform.controller;

import com.example.springsecurityform.log.SecurityLogger;
import com.example.springsecurityform.model.Account;
import com.example.springsecurityform.repository.AccountRepository;
import com.example.springsecurityform.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(@ModelAttribute Account account) {

        return accountService.createNew(account);
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {

        SecurityLogger.log("MVC");

        return () -> {
            SecurityLogger.log("Callable");
            return "Asnyc Handler";
        };
    }
}
