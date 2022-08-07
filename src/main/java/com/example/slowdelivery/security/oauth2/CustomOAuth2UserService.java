package com.example.slowdelivery.security.oauth2;

import com.example.slowdelivery.Customer.domain.Customer;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.security.oauth2.dto.OAuth2UserInfo;
import com.example.slowdelivery.security.oauth2.dto.OAuth2UserInfoFactory;
import com.example.slowdelivery.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import com.example.slowdelivery.user.domain.AuthProvider;
import com.example.slowdelivery.user.domain.Role;
import com.example.slowdelivery.user.domain.User;
import com.example.slowdelivery.Customer.repository.CustomerRepository;
import com.example.slowdelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final CustomerRepository customerRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("OAuth2 provider에 이메일이 없습니다.");
        }

        //TODO : getId()
        Optional<User> userOptional = customerRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if (!userOptional.isPresent()) {
            user = registerNewUser(userRequest, oAuth2UserInfo);
        } else {
            user = userOptional.get();
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        Customer customer = Customer.builder()
                .email(oAuth2UserInfo.getEmail())
                .name(oAuth2UserInfo.getName())
                .nickname(oAuth2UserInfo.getNickname())
                .provider(AuthProvider.valueOf(provider.toLowerCase()))
                .providerId(oAuth2UserInfo.getId())
                .role(Role.CUSTOMER)
                .build();

        return customerRepository.save(customer);
    }
}
