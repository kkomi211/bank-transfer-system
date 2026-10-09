package com.kkomi211.bank.member.controller;

import com.kkomi211.bank.member.dto.SignupRequest;
import com.kkomi211.bank.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody @Valid SignupRequest request) {
        Long memberId = memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
    }
}