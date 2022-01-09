package com.example.springsecurityform.service;

import com.example.springsecurityform.log.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    @Async
    public void asyncServie(){
        SecurityLogger.log("asyncServie");
    }
}
