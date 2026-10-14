package com.kkomi211.bank.account.controller;

import com.kkomi211.bank.account.dto.AccountResponse;
import com.kkomi211.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> openAccount(@AuthenticationPrincipal String email) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.openAccount(email));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getMyAccounts(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(accountService.getMyAccounts(email));
    }
}