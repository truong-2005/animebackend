package com.qtanime.animebackend.security.oauth;

import java.io.IOException;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.qtanime.animebackend.entity.Role;
import com.qtanime.animebackend.entity.User;
import com.qtanime.animebackend.enums.RoleName;
import com.qtanime.animebackend.repository.RoleRepository;
import com.qtanime.animebackend.repository.UserRepository;
import com.qtanime.animebackend.security.jwt.JwtTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            org.springframework.security.core.Authentication authentication
    ) throws IOException, ServletException {

        OAuth2AuthenticationToken token =
                (OAuth2AuthenticationToken) authentication;

        String email = token.getPrincipal()
                .getAttribute("email");

        String name = token.getPrincipal()
                .getAttribute("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    Role role = roleRepository.findByName(
                                    RoleName.CUSTOMER
                            )
                            .orElseThrow();

                    User newUser = User.builder()
                            .email(email)
                            .username(email)
                            .fullName(name)
                            .enabled(true)
                            .role(role)
                            .build();

                    return userRepository.save(newUser);
                });

        String jwt = jwtTokenProvider.generateToken(user);

        response.sendRedirect(
                "http://localhost:3000/oauth2/success?token="
                        + jwt
        );
    }
}