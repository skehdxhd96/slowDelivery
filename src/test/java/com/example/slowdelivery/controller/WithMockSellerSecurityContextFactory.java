package com.example.slowdelivery.controller;

import com.example.slowdelivery.domain.seller.Seller;
import com.example.slowdelivery.security.common.UserPrincipal;
import com.example.slowdelivery.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockSellerSecurityContextFactory implements WithSecurityContextFactory<WithMockSeller> {
    @Override
    public SecurityContext createSecurityContext(WithMockSeller customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Seller seller = new Seller();
        seller.setId(customUser.id());
        seller.setEmail(customUser.email());
        seller.setNickname(customUser.nickname());
        seller.setName(customUser.name());
        seller.setPassword(customUser.password());
        seller.setRole(customUser.roles());

        UserPrincipal principal = UserPrincipal.create(seller);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, seller.getPassword(),
                principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
