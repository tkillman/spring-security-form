package com.example.springsecurityform.repository;

import com.example.springsecurityform.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUsername(String username);
}
