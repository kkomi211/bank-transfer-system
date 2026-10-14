package com.kkomi211.bank.account.entity;

import com.kkomi211.bank.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private Account(Member member, String accountNumber) {
        this.member = member;
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public void deposit(BigDecimal amount) {
        validateActive();
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateActive();
        validateAmount(amount);
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }
        this.balance = this.balance.subtract(amount);
    }

    public boolean isOwnedBy(Long memberId) {
        return this.member.getId().equals(memberId);
    }

    private void validateActive() {
        if (this.status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("사용할 수 없는 계좌입니다.");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다.");
        }
    }
}