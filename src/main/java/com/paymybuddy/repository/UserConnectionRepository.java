package com.paymybuddy.repository;

import com.paymybuddy.model.User;
import com.paymybuddy.model.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserConnectionRepository
        extends JpaRepository<UserConnection, Integer> {

    boolean existsByUserAndConnection(
            User user,
            User connection);

    List<UserConnection> findByUser(User user);
}