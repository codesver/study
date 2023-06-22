package com.codesver.springsecurity.security.oauth;

import com.codesver.springsecurity.mvc.entity.Member;
import com.codesver.springsecurity.mvc.entity.Role;
import com.codesver.springsecurity.mvc.repsoitory.MemberRepository;
import com.codesver.springsecurity.security.PasswordEncoder;
import com.codesver.springsecurity.security.basic.PrincipleDetails;
import com.codesver.springsecurity.security.oauth.provider.GoogleUserInfo;
import com.codesver.springsecurity.security.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipleOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = distinguishProvider(userRequest.getClientRegistration().getRegistrationId(), oAuth2User);

        assert oAuth2UserInfo != null;
        Member member = Member.builder()
                .name(oAuth2UserInfo.getName())
                .userId(oAuth2UserInfo.getUserId())
                .userPw(passwordEncoder.encode(oAuth2UserInfo.getUserPw()))
                .role(Role.ROLE_USER).build();

        Optional<Member> optionalMember = memberRepository.findMemberByUserId(member.getUserId());
        member = optionalMember.orElse(memberRepository.save(member));
        return new PrincipleDetails(member, oAuth2User.getAttributes());
    }

    private OAuth2UserInfo distinguishProvider(String provider, OAuth2User oAuth2User) {
        if (provider.equals("google")) return new GoogleUserInfo(oAuth2User.getAttributes());
        return null;
    }
}