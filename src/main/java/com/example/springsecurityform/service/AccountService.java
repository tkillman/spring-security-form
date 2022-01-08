package com.example.springsecurityform.service;

import com.example.springsecurityform.model.Account;
import com.example.springsecurityform.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return Optional.ofNullable(username)
                .map(uname -> accountRepository.findByUsername(uname))
                .map(account ->
                        User.builder()
                                .username(account.getUsername())
                                .password(account.getPassword())
                                .roles(account.getRole()).build()
                )
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public Account createNew(Account account) {
        account.encodePassword(passwordEncoder);
        return accountRepository.save(account);
    }
}
