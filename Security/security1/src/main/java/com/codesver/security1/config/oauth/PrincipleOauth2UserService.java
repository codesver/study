package com.codesver.security1.config.oauth;

import com.codesver.security1.config.auth.PrincipleDetails;
import com.codesver.security1.config.CustomBCryptPasswordEncoder;
import com.codesver.security1.model.User;
import com.codesver.security1.repository.UserRepository;
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

    private final CustomBCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    // Google 로부터 받은 userRequest 데이터에 대한 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("Client Registration = " + userRequest.getClientRegistration());
        System.out.println("Access Token = " + userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("User Profile Info = " + oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getClientId();    // Google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;  // google_############...
        String password = encoder.encode("비밀번호");
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            System.out.println("First login by Google");
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(provider)
                    .build();
            userRepository.save(user);
            return new PrincipleDetails(user, oAuth2User.getAttributes());
        }
        return new PrincipleDetails(optionalUser.get(), oAuth2User.getAttributes());
    }
}
