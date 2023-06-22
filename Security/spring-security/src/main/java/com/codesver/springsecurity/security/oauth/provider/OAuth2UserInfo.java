package com.codesver.springsecurity.security.oauth.provider;

public interface OAuth2UserInfo {
    String getName();

    String getUserId();

    String getUserPw();

    String getProvider();

    String getProviderId();
}
