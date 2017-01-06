package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserRole;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.model.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context-test-mock.xml")
@WebAppConfiguration
public class AdminControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserDao userDao;

    private MockMvc mockMvc;

    private Role roleAdmin = new Role(1L, UserRole.ADMIN.name());
    private Role roleUser = new Role(2L, UserRole.USER.name());

    private User user1 = new User(1L,
            "admin",
            "Admin1",
            "admin@mail.ru",
            "admin",
            "adminovich",
            new Date(1990, 10, 10),
            roleAdmin);

    private User user2 = new User(2L,
            "ivan",
            "Ivan123",
            "ivan@mail.ru",
            "ivan",
            "grozniy",
            new Date(1530, 9, 3),
            roleUser);

    @Before
    public void setup() {
        Mockito.reset(userDao);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    @WithMockAdmin
    public void admin() throws Exception {
        when(userDao.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(forwardedUrl("/WEB-INF/pages/admin.jsp"))
                .andExpect(model().attribute("userList", hasSize(2)))
                .andExpect(model().attribute("userList", hasItem(user1)))
                .andExpect(model().attribute("userList", hasItem(user2)));
    }

    @Test
    @WithMockAdmin
    public void adminDelete() throws Exception {
        when(userDao.findById(2L)).thenReturn(user2);

        mockMvc.perform(get("/admin/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

    }

    @Test
    @WithMockAdmin
    public void adminDeleteBadId() throws Exception {
        doThrow(new UserNotFoundException()).when(userDao).findById(100L);

        mockMvc.perform(get("/admin/delete/100"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockAdmin
    public void adminCreate() throws Exception {
        mockMvc.perform(get("/admin/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(forwardedUrl("/WEB-INF/pages/user_form.jsp"))
                .andExpect(model().attributeExists("userModel"))
                .andExpect(model().attributeExists("roleNameList"))
                .andExpect(model().attribute("action", equalTo(AdminController.ACTION_CREATE_USER)));
    }

    @Test
    @WithMockAdmin
    public void adminEdit() throws Exception {
        when(userDao.findById(2L)).thenReturn(user2);
        mockMvc.perform(get("/admin/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(forwardedUrl("/WEB-INF/pages/user_form.jsp"))
                .andExpect(model().attribute("userModel", hasProperty("user", equalTo(user2))))
                .andExpect(model().attributeExists("roleNameList"))
                .andExpect(model().attribute("action", equalTo(AdminController.ACTION_EDIT_USER)));
    }

    @Test
    @WithMockAdmin
    public void adminEditBadId() throws Exception {
        doThrow(new UserNotFoundException()).when(userDao).findById(100L);
        mockMvc.perform(get("/admin/edit/100"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    @WithMockAdmin
    public void adminCreateUser() throws Exception {
        UserModel userModel = new UserModel(user2);
        doThrow(new UserNotFoundException()).when(userDao).findByLogin(user2.getLogin());
        doThrow(new UserNotFoundException()).when(userDao).findByEmail(user2.getEmail());

        mockMvc.perform(post("/admin/create")
                .param("user.login", userModel.getUser().getLogin())
                .param("user.email", userModel.getUser().getEmail())
                .param("user.password", userModel.getUser().getPassword())
                .param("user.firstName", userModel.getUser().getFirstName())
                .param("user.lastName", userModel.getUser().getLastName())
                .param("birthdayStr", userModel.getBirthdayStr())
                .param("passwordConfirm", userModel.getUser().getPassword())
                .param("roleName", userModel.getRoleName()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(forwardedUrl("/WEB-INF/pages/admin.jsp"));
    }

    @Test
    @WithMockAdmin
    public void adminEditUser() throws Exception {
        UserModel userModel = new UserModel(user2);
        doThrow(new UserNotFoundException()).when(userDao).findByEmail(user2.getEmail());

        mockMvc.perform(post("/admin/edit")
                .param("user.id", userModel.getUser().getId().toString())
                .param("user.login", userModel.getUser().getLogin())
                .param("user.email", userModel.getUser().getEmail())
                .param("user.password", userModel.getUser().getPassword())
                .param("user.firstName", userModel.getUser().getFirstName())
                .param("user.lastName", userModel.getUser().getLastName())
                .param("birthdayStr", userModel.getBirthdayStr())
                .param("passwordConfirm", userModel.getUser().getPassword())
                .param("roleName", userModel.getRoleName()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(forwardedUrl("/WEB-INF/pages/admin.jsp"));
    }


    @Test
    @WithMockAdmin
    public void adminCreateUserWithErrors() throws Exception {
        UserModel userModel = new UserModel(user2);

        User user3 = user1;
        user3.setLogin(user2.getLogin());
        user3.setEmail(user2.getEmail());

        when(userDao.findByLogin(user2.getLogin())).thenReturn(user3);
        when(userDao.findByLogin(user2.getEmail())).thenReturn(user3);

        mockMvc.perform(post("/admin/create")
                .param("user.login", userModel.getUser().getLogin())
                .param("user.email", userModel.getUser().getEmail())
                .param("user.password", userModel.getUser().getPassword())
                .param("user.firstName", userModel.getUser().getFirstName())
                .param("user.lastName", userModel.getUser().getLastName())
                .param("birthdayStr", userModel.getBirthdayStr())
                .param("passwordConfirm", userModel.getUser().getPassword())
                .param("roleName", userModel.getRoleName()))
                .andExpect(model().attributeHasFieldErrors("userModel", "user.login"))
                .andExpect(model().attributeHasFieldErrors("userModel", "user.email"))
                .andExpect(model().attributeExists("userModel"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(forwardedUrl("/WEB-INF/pages/user_form.jsp"));
    }


    @Test
    @WithMockAdmin
    public void adminEditUserWithErrors() throws Exception {
        when(userDao.findById(2L)).thenReturn(user2);

        UserModel userModel = new UserModel(user2);
        userModel.setPasswordConfirm("bad_pass123");

        mockMvc.perform(post("/admin/edit")
                .param("user.id", userModel.getUser().getId().toString())
                .param("user.login", userModel.getUser().getLogin())
                .param("user.email", userModel.getUser().getEmail())
                .param("user.password", userModel.getUser().getPassword())
                .param("user.firstName", userModel.getUser().getFirstName())
                .param("user.lastName", userModel.getUser().getLastName())
                .param("birthdayStr", userModel.getBirthdayStr())
                .param("passwordConfirm", userModel.getPasswordConfirm())
                .param("roleName", userModel.getRoleName()))
                .andExpect(model().attributeHasFieldErrors("userModel", "passwordConfirm"))
                .andExpect(model().attributeExists("userModel"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(forwardedUrl("/WEB-INF/pages/user_form.jsp"));
    }
}
