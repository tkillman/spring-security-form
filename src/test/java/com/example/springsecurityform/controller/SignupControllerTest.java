package com.example.springsecurityform.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SignupControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @Description("[회원가입 화면]")
    public void signupForm() throws Exception {
        mvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("_csrf")));
    }

    @Test
    @Description("[회원가입 요청] csrf 값이 없어서 실패")
    public void wrongProcessSignup() throws Exception {
        mvc.perform(post("/signup")
                        .param("username", "donggyu")
                        .param("password", "1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Description("[회원가입 요청] csrf 값 설정 후 성공")
    public void successProcessSignup() throws Exception {
        mvc.perform(post("/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "donggyu")
                        .param("password", "1"))
                .andExpect(status().is3xxRedirection()); // 300 redirect
    }
}