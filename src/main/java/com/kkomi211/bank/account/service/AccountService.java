package com.kkomi211.bank.account.service;

import com.kkomi211.bank.account.dto.AccountResponse;
import com.kkomi211.bank.account.entity.Account;
import com.kkomi211.bank.account.repository.AccountRepository;
import com.kkomi211.bank.member.entity.Member;
import com.kkomi211.bank.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final SecureRandom random = new SecureRandom();

    @Transactional
    public AccountResponse openAccount(String email) {
        Member member = findMember(email);
        Account account = Account.builder()
                .member(member)
                .accountNumber(generateAccountNumber())
                .build();
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getMyAccounts(String email) {
        Member member = findMember(email);
        return accountRepository.findAllByMemberIdOrderByCreatedAtDesc(member.getId())
                .stream()
                .map(AccountResponse::from)
                .toList();
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    private String generateAccountNumber() {
        String number;
        do {
            number = String.format("110-%03d-%06d", random.nextInt(1000), random.nextInt(1_000_000));
        } while (accountRepository.existsByAccountNumber(number));
        return number;
    }
}