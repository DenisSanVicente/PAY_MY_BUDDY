package com.paymybuddy.service;

import com.paymybuddy.exception.ConnectionAlreadyExistsException;
import com.paymybuddy.exception.InvalidOperationException;
import com.paymybuddy.exception.UserNotFoundException;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.User;
import com.paymybuddy.model.UserConnection;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.repository.UserConnectionRepository;
import com.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserConnectionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserConnectionRepository userConnectionRepository;

    @InjectMocks
    private UserConnectionService userConnectionService;


    @Test
    void addConnection_shouldSaveConnection() {

        //GIVEN
        User user1 = new User();
        user1.setIdUser(1);
        user1.setEmail("denis@test.fr");

        User user2 = new User();
        user2.setIdUser(2);
        user2.setEmail("alice@test.fr");

        when(userRepository.findByEmail("denis@test.fr")).thenReturn(Optional.of(user1));
        when(userRepository.findByEmail("alice@test.fr")).thenReturn(Optional.of(user2));

        // WHEN
        userConnectionService.addConnection(
                "denis@test.fr",
                "alice@test.fr"
        );

        // THEN
        verify(userConnectionRepository).save(any(UserConnection.class));
    }

    @Test
    void addConnection_shouldThrowUserNotFoundException_whenContactDoesNotExist() {

        // GIVEN
        User user = new User();
        user.setEmail("denis@test.fr");

        when(userRepository.findByEmail("denis@test.fr"))
                .thenReturn(Optional.of(user));

        when(userRepository.findByEmail("unknown@test.fr"))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThrows(UserNotFoundException.class,
                () -> userConnectionService.addConnection(
                        "denis@test.fr",
                        "unknown@test.fr"
                )
        );

        verify(userConnectionRepository, never())
                .save(any(UserConnection.class));
    }

    @Test
    void addConnection_shouldThrowInvalidOperationException_whenUserAddsHimself() {

        // GIVEN
        User user1 = new User();
        user1.setIdUser(1);
        user1.setEmail("denis@test.fr");

        when(userRepository.findByEmail("denis@test.fr")).thenReturn(Optional.of(user1));


        // WHEN / THEN
        assertThrows(InvalidOperationException.class,
                () -> userConnectionService.addConnection(
                        "denis@test.fr",
                        "denis@test.fr"
                ));

        verify(userConnectionRepository, never())
                .save(any(UserConnection.class));

        verify(userConnectionRepository, never())
                .existsByUserAndConnection(any(), any());
    }


    @Test
    void addConnection_shouldThrowConnectionAlreadyExistsException_whenConnectionAlreadyExists() {

        // GIVEN
        User user1 = new User();
        user1.setIdUser(1);
        user1.setEmail("denis@test.fr");

        User user2 = new User();
        user2.setIdUser(2);
        user2.setEmail("alice@test.fr");

        when(userRepository.findByEmail("denis@test.fr"))
                .thenReturn(Optional.of(user1));

        when(userRepository.findByEmail("alice@test.fr"))
                .thenReturn(Optional.of(user2));

        when(userConnectionRepository.existsByUserAndConnection(user1, user2))
                .thenReturn(true);

        // WHEN / THEN
        assertThrows(ConnectionAlreadyExistsException.class,
                () -> userConnectionService.addConnection(
                        "denis@test.fr",
                        "alice@test.fr"
                ));

        verify(userConnectionRepository, never())
                .save(any(UserConnection.class));
    }
}
