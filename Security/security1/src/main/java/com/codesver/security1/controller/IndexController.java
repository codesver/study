package com.codesver.security1.controller;

import com.codesver.security1.auth.PrincipleDetails;
import com.codesver.security1.model.User;
import com.codesver.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/test/normal/login")
    @ResponseBody
    public String testNormalLogin(
            Authentication authentication,
            @AuthenticationPrincipal PrincipleDetails userDetails) {    // 2가지 방법으로 getUser 호출 가능
        PrincipleDetails principleDetails = (PrincipleDetails) authentication.getPrincipal();
        System.out.println("principleDetails.getUser() = " + principleDetails.getUser());
        System.out.println("userDetails.getUsername() = " + userDetails.getUser());
        return "Session 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String testOAuthLogin(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauth) {    // 2가지 방법으로 getUser 호출 가능
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        System.out.println("oauth.getAttributes() = " + oauth.getAttributes());
        return "OAuth Session 정보 확인하기";
    }

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "Private Information";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    @ResponseBody
    public String data() {
        return "Data";
    }
}
