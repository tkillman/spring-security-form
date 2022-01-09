package com.example.springsecurityform.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

@Slf4j
public class SecurityLogger {

    public static void log(String message) {

        log.info(message);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Thread thread =Thread.currentThread();

        log.info(">>>>> thread {}", thread.getName());
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        String principalToString = Optional.ofNullable(principal)
                                    .map(Object::toString)
                                    .orElse("principal is null");
        log.info(">>>>> principal {}", principalToString);
    }
}
