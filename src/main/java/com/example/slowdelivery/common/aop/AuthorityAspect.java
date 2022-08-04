package com.example.slowdelivery.common.aop;

import com.example.slowdelivery.common.annotation.CurrentUser;
import com.example.slowdelivery.exception.AuthException;
import com.example.slowdelivery.exception.ErrorCode;
import com.example.slowdelivery.user.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorityAspect {

    @Before("@annotation(com.example.slowdelivery.common.annotation.CustomerOnly) && args(currentUser, ..)")
    public void CustomerUseOnly(User currentUser) {

        if(!currentUser.getRole().toString().equals("CUSTOMER")) {
            throw new AuthException(ErrorCode.CUSTOMER_ONLY_USED);
        }

    }

    @Before("@annotation(com.example.slowdelivery.common.annotation.SellerOnly) && args(currentUser, ..)")
    public void SellerUseOnly(User currentUser) {

        if(!currentUser.getRole().toString().equals("SELLER")) {
            throw new AuthException(ErrorCode.SELLER_ONLY_USED);
        }
    }
}
