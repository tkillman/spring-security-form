package com.example.springsecurityform.form;

import com.example.springsecurityform.log.SecurityLogger;
import com.example.springsecurityform.service.AccountService;
import com.example.springsecurityform.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.Callable;


@Controller
public class SampleController {

    @Autowired
    SampleService sampleService;

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

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {

        SecurityLogger.log("MVC");

        return () -> {
            SecurityLogger.log("Callable");
            return "Asnyc Handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC before asyncService");
        sampleService.asyncServie();
        SecurityLogger.log("MVC after asyncService");

        return "Async Service";
    }
}
