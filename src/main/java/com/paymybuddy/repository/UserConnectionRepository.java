package com.paymybuddy.repository;

import com.paymybuddy.model.User;
import com.paymybuddy.model.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserConnectionRepository extends JpaRepository<UserConnection, Integer> {

    // ===== DETECTION DES DOUBLONS ===== //
    boolean existsByUserAndConnection(User user, User connection);

}
