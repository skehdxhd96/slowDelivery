package com.example.slowdelivery.security.oauth2.dto;

import java.util.Map;

public class NaverOauth2UserInfo extends OAuth2UserInfo{

    public NaverOauth2UserInfo(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("response"));
    }

    @Override
    public String getId() { return (String) attributes.get("id");}

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("nickname");
    }
}
