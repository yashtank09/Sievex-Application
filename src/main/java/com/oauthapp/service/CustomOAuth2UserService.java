package com.oauthapp.service;

import com.oauthapp.entity.Users;
import com.oauthapp.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = new DefaultOAuth2UserService().loadUser(userRequest);
        String email = oauthUser.getAttribute("email");

        Users user = userRepository.findByEmail(email).orElseGet(() -> {
                    Users newUser = new Users();
                    newUser.setEmail(email);
                    newUser.setUserName(oauthUser.getAttribute("name"));
                    newUser.setProfileCompleted(false);
                    return userRepository.save(newUser);
                });

        // Attach custom attributes
        Map<String, Object> attributes = new HashMap<>(oauthUser.getAttributes());
        attributes.put("profileComplete", user.isProfileCompleted());

        return new DefaultOAuth2User(
                oauthUser.getAuthorities(),
                attributes,
                "email"
        );
    }
}
