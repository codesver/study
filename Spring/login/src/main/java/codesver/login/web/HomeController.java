package codesver.login.web;

import codesver.login.domain.member.Member;
import codesver.login.domain.member.MemberRepository;
import codesver.login.web.argumentresolver.Login;
import codesver.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null)
            return "home";

        //
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null)
            return "home";

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Long memberId, Model model) {
        // Search member info saved at session manager
        Member member = (Member) sessionManager.getSession(request);

        // Login
        if (member == null)
            return "home";

        model.addAttribute("member", member);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Long memberId, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return "home";

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // Login
        if (loginMember == null)
            return "home";

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Long memberId, Model model) {
        if (loginMember == null)
            return "home";
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Long memberId, Model model) {
        if (loginMember == null)
            return "home";
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}