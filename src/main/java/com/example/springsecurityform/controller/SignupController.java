package com.example.springsecurityform.controller;

import com.example.springsecurityform.model.Account;
import com.example.springsecurityform.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String signup(Model model){
        model.addAttribute("account", new Account());
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute Account account){

        accountService.createNew(account);
        return "redirect:/";
    }
}
