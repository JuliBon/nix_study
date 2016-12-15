package com.nixsolutions.bondarenko.study.controller;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
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

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @WithMockAdmin
    public void admin() throws Exception {

        User user1 = new User(1L,
                "admin",
                "Admin1",
                "admin@mail.ru",
                "admin",
                "adminovich",
                new Date(1990, 10, 10),
                roleAdmin);

        User user2 = new User(2L,
                "ivan",
                "Ivan123",
                "ivan@mail.ru",
                "ivan",
                "grozniy",
                new Date(1530, 9, 3),
                roleUser);

        when(userDao.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(forwardedUrl("/WEB-INF/pages/admin.jsp"))

                .andExpect(model().attribute("userList", hasSize(2)))
                .andExpect(model().attribute("userList", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("login", is("admin")),
                                hasProperty("email", is("admin@mail.ru")),
                                hasProperty("password", is("Admin1")),
                                hasProperty("firstName", is("admin")),
                                hasProperty("lastName", is("adminovich")),
                                hasProperty("birthday", is(new Date(1990, 10, 10))),
                                hasProperty("role", is(roleAdmin))
                        )
                )))
                .andExpect(model().attribute("userList", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("login", is("ivan")),
                                hasProperty("password", is("Ivan123")),
                                hasProperty("firstName", is("ivan")),
                                hasProperty("lastName", is("grozniy")),
                                hasProperty("birthday", is(new Date(1530, 9, 3))),
                                hasProperty("role", is(roleUser))
                        )
                )));
/*        verify(userDao, times(1)).findAll();
        verifyNoMoreInteractions(userDao);*/
    }
}
