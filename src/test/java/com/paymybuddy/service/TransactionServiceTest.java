package com.paymybuddy.service;

import com.paymybuddy.exception.InsufficientBalanceException;
import com.paymybuddy.exception.InvalidOperationException;
import com.paymybuddy.exception.UserNotFoundException;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.Transaction;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void transferMoneyOk() {

        // Given
        User sender = new User();
        sender.setIdUser(1);
        sender.setEmail("denis@test.fr");

        User receiver = new User();
        receiver.setIdUser(2);
        receiver.setEmail("alice@test.fr");

        Account senderAccount = new Account();
        senderAccount.setBalance(new BigDecimal("100.00"));

        Account receiverAccount = new Account();
        receiverAccount.setBalance(new BigDecimal("50.00"));

        when(userRepository.findByEmail("denis@test.fr")).thenReturn(Optional.of(sender));
        when(userRepository.findByEmail("alice@test.fr")).thenReturn(Optional.of(receiver));
        when(accountRepository.findByUser(sender)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUser(receiver)).thenReturn(Optional.of(receiverAccount));

        // When
        transactionService.transferMoney("denis@test.fr",
                "alice@test.fr",
                new BigDecimal("50.00"),
                "Test de transfert");

        // Then
        assertEquals(new BigDecimal("50.00"), senderAccount.getBalance());
        assertEquals(new BigDecimal("100.00"), receiverAccount.getBalance());

        verify(accountRepository).save(senderAccount);
        verify(accountRepository).save(receiverAccount);
        verify(transactionRepository).save(any(Transaction.class));
    }


    @Test
    void transferMoney_shouldThrowExceptionWhenSenderNotFound() {

        when(userRepository.findByEmail("unknown@test.fr")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> transactionService.transferMoney(
                        "unknown@test.fr",
                        "denis@test.fr",
                        new BigDecimal("10.00"),
                        "Transfert Test"
                ));

        verify(transactionRepository, never()).save(any(Transaction.class));
    }


    @Test
    void transferMoney_shouldThrowExceptionWhenAmountIsNegative() {

        // GIVEN
        User sender = new User();
        User receiver = new User();

        when(userRepository.findByEmail("denis@test.fr")).thenReturn(Optional.of(sender));
        when(userRepository.findByEmail("alice@test.fr")).thenReturn(Optional.of(receiver));

        assertThrows(InvalidOperationException.class,
                () -> transactionService.transferMoney(
                        "denis@test.fr",
                        "alice@test.fr",
                        new BigDecimal("-10.00"),
                        "Transfert Test"
                ));

        verify(transactionRepository, never()).save(any(Transaction.class));
    }



    @Test
    void transferMoney_shouldThrowExceptionWhenBalanceIsInsufficient() {

        User sender = new User();
        sender.setEmail("denis@test.fr");

        User receiver = new User();
        receiver.setEmail("alice@test.fr");

        Account senderAccount = new Account();
        senderAccount.setBalance(new BigDecimal("50.00"));

        Account receiverAccount = new Account();
        receiverAccount.setBalance(new BigDecimal("50.00"));

        when(userRepository.findByEmail("denis@test.fr")).thenReturn(Optional.of(sender));
        when(userRepository.findByEmail("alice@test.fr")).thenReturn(Optional.of(receiver));
        when(accountRepository.findByUser(sender)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUser(receiver)).thenReturn(Optional.of(receiverAccount));


        // THEN
        assertThrows(InsufficientBalanceException.class,
                () -> transactionService.transferMoney(
                        "denis@test.fr",
                        "alice@test.fr",
                        new BigDecimal("100.00"),
                        "Transfert Test"
                ));

        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
