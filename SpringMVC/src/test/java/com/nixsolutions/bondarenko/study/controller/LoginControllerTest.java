package com.nixsolutions.bondarenko.study.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setViewResolvers(viewResolver())
                .alwaysExpect(status().isOk())
                .alwaysExpect(view().name("login"))
                .alwaysExpect(forwardedUrl("/WEB-INF/pages/login.jsp"))
                .build();
    }


    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(get("/login"));
    }

    @Test
    public void logoutTest() throws Exception {
        mockMvc.perform(get("/logout"));
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
