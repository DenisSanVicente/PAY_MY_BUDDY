package com.paymybuddy.service;

import com.paymybuddy.exception.ConnectionAlreadyExistsException;
import com.paymybuddy.exception.InvalidOperationException;
import com.paymybuddy.exception.UserNotFoundException;
import com.paymybuddy.model.User;
import com.paymybuddy.model.UserConnection;
import com.paymybuddy.repository.UserConnectionRepository;
import com.paymybuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserConnectionService {

    private final UserConnectionRepository userConnectionRepository;
    private UserRepository userRepository;

    public UserConnectionService(UserConnectionRepository userConnectionRepository, UserRepository userRepository) {
        this.userConnectionRepository = userConnectionRepository;
        this.userRepository = userRepository;
    }

    public void addConnection(String userEmail, String connetionEmail) {

        // ===== RECHERCHE DE L'UTILISATEUR ===== //
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable "));

        User connection = userRepository.findByEmail(connetionEmail)
                .orElseThrow(() -> new UserNotFoundException("Contact introuvable"));

        // ===== VERIFICATION QUE USER ET USER CONNECTION EXISTENT ===== //
        if (user.getIdUser().equals(connection.getIdUser())) {
            throw new InvalidOperationException("Vous ne pouvez pas vous ajouter vous-même");
        }

        // ===== VERIFICATION D'UN DOUBLON ===== //
        if (userConnectionRepository.existsByUserAndConnection(user, connection)) {
            throw new ConnectionAlreadyExistsException("Ce contact existe déjà");
        }

        // ===== CREATION DE L'UTILISATEUR ===== //
        UserConnection userConnection = new UserConnection();
        userConnection.setUser(user);
        userConnection.setConnection(connection);

        userConnectionRepository.save(userConnection);
    }
}
