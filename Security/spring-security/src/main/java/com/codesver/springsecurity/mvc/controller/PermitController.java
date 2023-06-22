package com.codesver.springsecurity.mvc.controller;

import com.codesver.springsecurity.mvc.entity.Member;
import com.codesver.springsecurity.mvc.entity.Role;
import com.codesver.springsecurity.mvc.repsoitory.MemberRepository;
import com.codesver.springsecurity.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PermitController {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository repository;

    @GetMapping
    public String getHomepage() {
        log.info("GET LOCALHOST");
        return "/permit/homepage";
    }

    @GetMapping("/login-form")
    public String getLoginForm() {
        return "/permit/login-form";
    }

    @GetMapping("/join-form")
    public String getJoinForm() {
        return "/permit/join-form";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute Member member, Model model) {
        member.setUserPw(passwordEncoder.encode(member.getUserPw()));
        member.setRole(Role.ROLE_USER);
        repository.save(member);
        return "redirect:/login-form";
    }
}
