package com.kkomi211.bank.account.repository;

import com.kkomi211.bank.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(String accountNumber);
    List<Account> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);
}