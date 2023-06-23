package com.codesver.springsecurity.security.oauth.provider;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getUserId() {
        return getName() + "_" + getProvider() + "_" + getProviderId() + "_" + "id";
    }

    @Override
    public String getUserPw() {
        return getName() + "_" + getProvider() + "_" + getProviderId() + "_" + "pw";
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }
}
