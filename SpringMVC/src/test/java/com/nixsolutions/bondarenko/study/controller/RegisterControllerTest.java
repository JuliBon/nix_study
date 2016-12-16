package com.nixsolutions.bondarenko.study.controller;


import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.model.UserModel;
import com.nixsolutions.bondarenko.study.recaptcha.VerifyUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context-test-mock.xml")
@WebAppConfiguration
public class RegisterControllerTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private VerifyUtils verifyUtils;


    private MockMvc mockMvc;

    private User userValid = new User(2L,
            "ivan",
            "Ivan123",
            "ivan@mail.ru",
            "ivan",
            "grozniy",
            new Date(1530, 9, 3),
            new Role(UserLibraryRole.USER.getId(), UserLibraryRole.USER.getName()));

    @Before
    public void setup() {
        Mockito.reset(userDao);
        Mockito.reset(verifyUtils);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    @WithAnonymousUser
    public void register() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(model().attributeExists("userModel"))
                .andExpect(forwardedUrl("/WEB-INF/pages/user_form.jsp"))
                .andExpect(model().attribute("action", equalTo(RegisterController.ACTION_REGISTER_USER)));
    }


    @Test
    public void registerUserNotValid() throws Exception {
        User user = new User(2L,
                "ivan",
                "Ivan123",
                "bad_email",
                "",
                "",
                null,
                null);

        UserModel userModel = new UserModel(user);

        doThrow(new UserNotFoundException()).when(userDao).findByLogin(user.getLogin());

        mockMvc.perform(post("/register")
                .param("user.login", userModel.getUser().getLogin())
                .param("user.email", userModel.getUser().getEmail())
                .param("user.password", userModel.getUser().getPassword())
                .param("user.firstName", userModel.getUser().getFirstName())
                .param("user.lastName", userModel.getUser().getLastName())
                .param("birthdayStr", userModel.getBirthdayStr())
                .param("passwordConfirm", userModel.getUser().getPassword())
                .param("roleName", userModel.getRoleName()))
                .andExpect(model().attributeHasFieldErrors("userModel", "user.email"))
                .andExpect(model().attributeHasFieldErrors("userModel", "user.firstName"))
                .andExpect(model().attributeHasFieldErrors("userModel", "user.lastName"))
                .andExpect(model().attributeHasFieldErrors("userModel", "birthdayStr"))
                .andExpect(model().attributeExists("userModel"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(forwardedUrl("/WEB-INF/pages/user_form.jsp"))
                .andExpect(model().attribute("action", equalTo(RegisterController.ACTION_REGISTER_USER)));

    }

    @Test
    @WithMockAdmin
    public void adminRegisterUserValid() throws Exception {
        UserModel userModel = new UserModel(userValid);

        when(verifyUtils.verify(any())).thenReturn(true);
        doThrow(new UserNotFoundException()).when(userDao).findByLogin(userValid.getLogin());
        doThrow(new UserNotFoundException()).when(userDao).findByEmail(userValid.getEmail());

        mockMvc.perform(post("/register")
                .param("user.login", userModel.getUser().getLogin())
                .param("user.email", userModel.getUser().getEmail())
                .param("user.password", userModel.getUser().getPassword())
                .param("user.firstName", userModel.getUser().getFirstName())
                .param("user.lastName", userModel.getUser().getLastName())
                .param("birthdayStr", userModel.getBirthdayStr())
                .param("passwordConfirm", userModel.getUser().getPassword())
                .param("roleName", userModel.getRoleName()))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("/WEB-INF/pages/login.jsp"));

        //verify(verifyUtils).verify(any());
        //verify(userDao).create(any(userValid.getClass()));
    }


    @Test
    @WithMockAdmin
    public void adminRegisterUserCaptchaError() throws Exception {
        UserModel userModel = new UserModel(userValid);

        doThrow(new UserNotFoundException()).when(userDao).findByLogin(userValid.getLogin());
        doThrow(new UserNotFoundException()).when(userDao).findByEmail(userValid.getEmail());
        when(verifyUtils.verify(any())).thenReturn(false);

        mockMvc.perform(post("/register")
                .param("user.login", userModel.getUser().getLogin())
                .param("user.email", userModel.getUser().getEmail())
                .param("user.password", userModel.getUser().getPassword())
                .param("user.firstName", userModel.getUser().getFirstName())
                .param("user.lastName", userModel.getUser().getLastName())
                .param("birthdayStr", userModel.getBirthdayStr())
                .param("passwordConfirm", userModel.getUser().getPassword())
                .param("roleName", userModel.getRoleName()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("captchaError"))
                .andExpect(view().name("user_form"))
                .andExpect(forwardedUrl("/WEB-INF/pages/user_form.jsp"));

        //verify(verifyUtils).verify(any());
    }
}
