package com.paymybuddy.service;

import com.paymybuddy.exception.EmailAlreadyExistsException;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.repository.UserConnectionRepository;
import com.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private UserService userService;



    @Test
    void registerUser_shouldSaveUserAndCreateAccount() {

        // GIVEN


        when(userRepository.findByEmail("denis@test.fr"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("motdepasse"))
                .thenReturn("motdepasseEncode");

        User savedUser = new User();
        savedUser.setIdUser(1);
        savedUser.setEmail("denis@test.fr");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        // WHEN
        userService.registerUser(
                "Denis",
                "denis@test.fr",
                "motdepasse"
        );

        // THEN
        verify(userRepository).findByEmail("denis@test.fr");
        verify(passwordEncoder).encode("motdepasse");
        verify(userRepository).save(any(User.class));
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void registerUser_shouldThrowEmailAlreadyExistsException_whenEmailAlreadyExists() {

        // GIVEN
        User existingUser = new User();
        existingUser.setEmail("denis@test.fr");

        when(userRepository.findByEmail("denis@test.fr"))
                .thenReturn(Optional.of(existingUser));

        // WHEN / THEN
        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.registerUser(
                        "Denis",
                        "denis@test.fr",
                        "motdepasse"
                ));

        verify(userRepository, never()).save(any(User.class));
        verify(accountRepository, never()).save(any(Account.class));
    }
}
