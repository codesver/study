package com.codesver.security1.config.oauth;

import com.codesver.security1.config.CustomBCryptPasswordEncoder;
import com.codesver.security1.config.auth.PrincipleDetails;
import com.codesver.security1.config.oauth.provider.FacebookUserInfo;
import com.codesver.security1.config.oauth.provider.GoogleUserInfo;
import com.codesver.security1.config.oauth.provider.NaverUserInfo;
import com.codesver.security1.config.oauth.provider.OAuth2UserInfo;
import com.codesver.security1.model.User;
import com.codesver.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipleOauth2UserService extends DefaultOAuth2UserService {

    private final CustomBCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    // OAuth API 로부터 받은 userRequest 데이터에 대한 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Client Registration = {}", userRequest.getClientRegistration());
        log.info("Access Token = {}", userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("User Profile Info = {}", oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();

        switch (provider) {
            case "google":
                oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
                break;
            case "facebook":
                oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
                break;
            case "naver":
                oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
                break;
        }

        assert oAuth2UserInfo != null;
        User user = new User(oAuth2UserInfo);
        user.setPassword(encoder.encode("비밀번호"));
        user.setRole("ROLE_USER");

        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isEmpty()) {
            userRepository.save(user);
            return new PrincipleDetails(user, oAuth2User.getAttributes());
        }
        return new PrincipleDetails(optionalUser.get(), oAuth2User.getAttributes());
    }
}
