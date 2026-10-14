package com.kkomi211.bank.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balanceAfter;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private AccountHistory(Account account, TransactionType type, BigDecimal amount) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = account.getBalance();
        this.createdAt = LocalDateTime.now();
    }

    public static AccountHistory of(Account account, TransactionType type, BigDecimal amount) {
        return new AccountHistory(account, type, amount);
    }
}