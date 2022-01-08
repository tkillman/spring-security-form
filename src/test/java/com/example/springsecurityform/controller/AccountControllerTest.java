package com.example.springsecurityform.controller;

import com.example.springsecurityform.model.Account;
import com.example.springsecurityform.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Test
    @Description("[index 접근] with 옵션을 주지 않은 채 사용")
    public void index_anonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Description("[index 접근] with 옵션을 주지 않은 채 사용")
    // @WithAnonymousUser
    public void index_anonymous2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/").with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Description("[index 접근] with donggyu로 접근")
    // @WithMockUser(username = "donggyu", roles = "USER")
    public void index_donggyu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .with(SecurityMockMvcRequestPostProcessors.user("donggyu")
                                .roles("USER")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Description("[admin 접근] with donggyu로 접근")
    public void admin_donggyu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin")
                        .with(SecurityMockMvcRequestPostProcessors.user("donggyu")
                                .roles("USER")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @Description("[admin 접근] with admin으로 접근")
    public void admin_admin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional // @Transactional을 붙이는 이유는 각 테스트마다 다시 rollback 해주기 때문이다. 붙여주지 않으면 username이 겹쳐서 에러 발생
    @Description("[login 성공] 로그인 성공 테스트")
    public void login_success() throws Exception{
        String username = "donggyu";
        String password = "123";

        Account account = createUser(username, password);
        // Tip account.getPassword()로 비교하면 안된다. password는 encode에 의해 암호화되기 때문에
        mockMvc.perform(formLogin().user(username).password(password))
                .andDo(print())
                .andExpect(authenticated());
    }

    @Test
    @Transactional
    @Description("[login 실패] 로그인 실패 테스트")
    public void login_fail() throws Exception{
        String username = "donggyu";
        String password = "123";

        Account account = createUser(username, password);
        // Tip account.getPassword()로 비교하면 안된다. password는 encode에 의해 암호화되기 때문에
        mockMvc.perform(formLogin().user(username).password("1234"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    private Account createUser(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("USER");
        return accountService.createNew(account);
    }
}