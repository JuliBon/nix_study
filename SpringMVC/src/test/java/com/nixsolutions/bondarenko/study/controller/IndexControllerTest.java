package com.nixsolutions.bondarenko.study.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context-test-mock.xml")
@WebAppConfiguration
public class IndexControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @WithAnonymousUser
    public void rootAnonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"));
    }

    @Test
    @WithMockUser
    public void rootUser() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("index"));
    }


    @Test
    @WithAnonymousUser
    public void indexAnonymous() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"));

    }

    @Test
    @WithMockUser
    public void indexUser() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(forwardedUrl("/WEB-INF/pages/home.jsp"));

    }

    @Test
    @WithMockAdmin
    public void indexAdmin() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("admin"));

    }


}
