package com.codesver.springsecurity.mvc.controller;

import com.codesver.springsecurity.mvc.entity.Member;
import com.codesver.springsecurity.mvc.repsoitory.MemberRepository;
import com.codesver.springsecurity.security.PrincipleDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository repository;

    @GetMapping
    public String getMember(@AuthenticationPrincipal PrincipleDetails principleDetails, Model model) {
        Member member = principleDetails.getMember();
        model.addAttribute("member", member);
        return "/member/member-info";
    }
}
