package com.codesver.security1.config.oauth.provider;

public interface OAuth2UserInfo {
    String getUsername();
    String getName();
    String getProvider();
    String getProviderId();
    String getEmail();
}
