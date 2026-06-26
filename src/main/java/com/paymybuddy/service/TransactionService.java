package com.paymybuddy.service;

import com.paymybuddy.exception.AccountNotFoundException;
import com.paymybuddy.exception.InsufficientBalanceException;
import com.paymybuddy.exception.InvalidOperationException;
import com.paymybuddy.exception.UserNotFoundException;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.model.Transaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // ===== METHODE DE TRANSFERT ===== //
    @Transactional
    public void transferMoney(
            String senderEmail,
            String receiverEmail,
            BigDecimal amount,
            String description) {

        // Recherche de l'expéditeur
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() ->
                        new UserNotFoundException("Expéditeur introuvable"));

        // Recherche du destinataire
        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() ->
                        new UserNotFoundException("Destinataire introuvable"));

        // Vérification du montant
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOperationException(
                    "Le montant doit être supérieur à zéro");
        }

        // Récupération des comptes
        Account senderAccount = accountRepository.findByUser(sender)
                .orElseThrow(() ->
                        new AccountNotFoundException("Compte expéditeur introuvable"));

        Account receiverAccount = accountRepository.findByUser(receiver)
                .orElseThrow(() ->
                        new AccountNotFoundException("Compte destinataire introuvable"));

        // Vérification du solde
        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Solde insuffisant");
        }

        // Débit
        senderAccount.setBalance(
                senderAccount.getBalance().subtract(amount));

        // Crédit
        receiverAccount.setBalance(
                receiverAccount.getBalance().add(amount));

        // Création de la transaction
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setDateTransaction(LocalDateTime.now());

        // Sauvegardes
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
        transactionRepository.save(transaction);
    }
}
