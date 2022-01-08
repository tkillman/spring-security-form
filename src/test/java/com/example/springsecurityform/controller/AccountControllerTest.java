package com.example.springsecurityform.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Description("[index 접근] with 옵션을 주지 않은 채 사용")
    public void index_anonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Description("[index 접근] with 옵션을 주지 않은 채 사용")
    // @WithAnonymousUser
    public void index_anonymous2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/").with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Description("[index 접근] with donggyu로 접근")
    // @WithMockUser(username = "donggyu", roles = "USER")
    public void index_donggyu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .with(SecurityMockMvcRequestPostProcessors.user("donggyu")
                                .roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Description("[admin 접근] with donggyu로 접근")
    public void admin_donggyu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin")
                        .with(SecurityMockMvcRequestPostProcessors.user("donggyu")
                                .roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Description("[admin 접근] with admin으로 접근")
    public void admin_admin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}