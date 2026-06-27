package com.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Integer idTransaction;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "date_transaction", nullable = false)
    private LocalDateTime dateTransaction;

    // ===== EXPÉDITEUR =====

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // ===== DESTINATAIRE =====

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
}