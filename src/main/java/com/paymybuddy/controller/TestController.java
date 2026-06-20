package com.paymybuddy.controller;

import com.paymybuddy.service.TransactionService;
import com.paymybuddy.service.UserConnectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TestController {

    private final UserConnectionService userConnectionService;
    private final TransactionService transactionService;

    public TestController(UserConnectionService userConnectionService,
                          TransactionService transactionService) {
        this.userConnectionService = userConnectionService;
        this.transactionService = transactionService;
    }

    @GetMapping("/test")
    public String testConnection() {

        userConnectionService.addConnection(
                "denis@test.fr",
                "alice@test.fr"
        );

        return "Connexion ajoutée";
    }

    @GetMapping("/test-transfer")
    public String testTransfer() {

        transactionService.transferMoney(
                "denis@test.fr",
                "alice@test.fr",
                new BigDecimal("20.00"),
                "Test de transfert"
        );

        return "Transfert effectué";
    }
}