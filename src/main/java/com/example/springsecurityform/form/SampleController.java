package com.example.springsecurityform.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;


@Controller
public class SampleController {
    @GetMapping("/")
    public String index(Model model, Principal principal) {

        String message = Optional.ofNullable(principal).orElse(() -> "").getName();

        model.addAttribute("message", "Hello Spring Security" + message);
        return "index";
    }

    @GetMapping("/info")
    public String index(Model model) {

        model.addAttribute("message", "Hello Spring info");
        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        model.addAttribute("message", "Hello Spring dashboard" + principal.getName());
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {

        model.addAttribute("message", "Hello Spring admin" + principal.getName());
        return "admin";
    }


}
