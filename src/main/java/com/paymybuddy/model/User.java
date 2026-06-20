package com.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // ===== RELATION 1:1 AVEC ACCOUNT =====

    @OneToOne(mappedBy = "user")
    private Account account;

    // ===== TRANSACTIONS ENVOYÉES =====

    @OneToMany(mappedBy = "sender")
    private List<Transaction> sentTransactions = new ArrayList<>();

    // ===== TRANSACTIONS REÇUES =====

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> receivedTransactions = new ArrayList<>();

    // ===== CONNEXIONS (AMIS) =====

    @OneToMany(mappedBy = "user")
    private List<UserConnection> contacts = new ArrayList<>();
}