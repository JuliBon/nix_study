package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private Role roleAdmin = new Role(UserLibraryRole.ADMIN.getId(), UserLibraryRole.ADMIN.getName());
    private Role roleUser = new Role(UserLibraryRole.USER.getId(), UserLibraryRole.USER.getName());

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

        //verify(userDao, times(1)).findAll().containsAll(Arrays.asList(user1, user2));
    }

    @Test
    @WithMockAdmin
    public void adminDelete() throws Exception {
        when(userDao.findById(2L)).thenReturn(user2);
        doNothing().when(userDao).remove(user2);

        mockMvc.perform(get("/admin/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("admin"));

        //verify(userDao).findById(2L);
        //verify(userDao).remove(user2);
    }

    @Test //(expected = UserNotFoundException.class)
    @WithMockAdmin
    public void adminDeleteBadId() throws Exception {
        doThrow(new UserNotFoundException()).when(userDao).findById(100L);

        mockMvc.perform(get("/admin/delete/100"))
                .andExpect(status().is4xxClientError());
        //verify(userDao).findById(100L);
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
        verify(userDao).findById(2L);
    }

    @Test(expected = UserNotFoundException.class)
    @WithMockAdmin
    public void adminEditBadId() throws Exception {
        doThrow(new UserNotFoundException()).when(userDao).findById(100L);
        mockMvc.perform(get("/admin/edit/100"))
                .andExpect(status().is4xxClientError());
        //verify(userDao).findById(100L);
    }
}
