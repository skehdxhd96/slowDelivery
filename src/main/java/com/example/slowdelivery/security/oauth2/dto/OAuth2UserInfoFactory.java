package com.example.slowdelivery.security.oauth2.dto;

import com.example.slowdelivery.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import com.example.slowdelivery.user.domain.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {
        throw new IllegalStateException("OAuth2UserInfoFactory의 인스턴스는 생성할 수 없습니다.");
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {

        if (registrationId.equalsIgnoreCase(AuthProvider.naver.toString())) {
            return new NaverOauth2UserInfo(attributes);
        }
        throw new OAuth2AuthenticationProcessingException(
                registrationId + " 로그인은 지원하지 않습니다.");
    }
}
