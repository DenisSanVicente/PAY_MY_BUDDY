package com.paymybuddy.integration;

import com.paymybuddy.model.Transaction;
import com.paymybuddy.model.User;
import com.paymybuddy.model.UserConnection;
import com.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.repository.UserConnectionRepository;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.TransactionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class TransferController {

    private final UserRepository userRepository;
    private final UserConnectionRepository userConnectionRepository;
    private TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public TransferController(
            UserRepository userRepository,
            UserConnectionRepository userConnectionRepository,
            TransactionRepository transactionRepository,
            TransactionService transactionService) {

        this.userRepository = userRepository;
        this.userConnectionRepository = userConnectionRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @GetMapping("/transfer")
    public String transfer(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        List<UserConnection> connections =
                userConnectionRepository.findByUser(user);

        List<Transaction> transactions =
                transactionRepository.findBySender(user);

        model.addAttribute(
                "connections",
                connections);

        model.addAttribute(
                "transactions",
                transactions);

        return "transfer";
    }

    @PostMapping("/transfer")
    public String makeTransfer(
            Authentication authentication,
            @RequestParam Integer receiverId,
            @RequestParam String description,
            @RequestParam BigDecimal amount) {

        String senderEmail = authentication.getName();

        User receiver = userRepository
                .findById(receiverId)
                .orElseThrow();

        transactionService.transferMoney(
                senderEmail,
                receiver.getEmail(),
                amount,
                description
        );

        return "redirect:/transfer";
    }

}
