package com.paymybuddy.repository;

import com.paymybuddy.model.Account;
import com.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByUser(User user);

}
