package com.kkomi211.bank.account.repository;

import com.kkomi211.bank.account.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
    List<AccountHistory> findAllByAccountIdOrderByCreatedAtDesc(Long accountId);
}