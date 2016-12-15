package com.nixsolutions.bondarenko.study.controller;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(roles =  "ADMIN")
public @interface WithMockAdmin {
}