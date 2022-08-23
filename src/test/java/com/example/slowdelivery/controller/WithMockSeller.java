package com.example.slowdelivery.controller;

import com.example.slowdelivery.user.domain.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockSellerSecurityContextFactory.class)
public @interface WithMockSeller {
    long id() default 1L;
    String nickname() default "hahaha";
    String email() default "hahaha@email.com";

    String name() default "helloworld";
    String password() default "password";

    Role roles() default Role.SELLER;
}
