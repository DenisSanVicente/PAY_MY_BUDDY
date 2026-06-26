package com.paymybuddy.service;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;


    @Test
    void loadUserByUsername_shouldReturnUserDetails() {

        // GIVEN
        User user = new User();
        user.setEmail("denis@test.fr");
        user.setPassword("motdepasse");

        when(userRepository.findByEmail("denis@test.fr")).thenReturn(Optional.of(user));

        // WHEN
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        // THEN
        assertEquals("denis@test.fr", userDetails.getUsername());
        assertEquals("motdepasse", userDetails.getPassword());

        verify(userRepository).findByEmail("denis@test.fr");
    }


    @Test
    void loadUserByName_shouldThrowUsernameNotFoundException() {

        // GIVEN
        when(userRepository.findByEmail("unknown@test.fr")).thenReturn(Optional.empty());

        // THEN
        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("unknown@test.fr"));

        verify(userRepository).findByEmail("unknown@test.fr");
    }


}
