package com.nixsolutions.bondarenko.study.rest;

import com.nixsolutions.bondarenko.study.dao.RoleDao;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
import com.nixsolutions.bondarenko.study.rest.resource.UsersResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.sql.Date;
import java.util.List;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserResourceTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    private HttpServer server;
    private WebTarget target;
    private static final URI BASE_URI = URI.create("http://localhost:8081/rest");

    private Role roleAdmin = new Role(1L, UserLibraryRole.ADMIN.name());
    private Role roleUser = new Role(1L, UserLibraryRole.USER.name());

    private User userAdmin = new User(null, "admin", "Admin1", "admin@mail.ru",
            "admin", "adminov", Date.valueOf("1980-08-08"), roleAdmin);
    private User userUser = new User(null, "ivan", "Qwe1", "ivan@mail.ru",
            "ivan", "grozniy", Date.valueOf("1530-9-03"), roleUser);
    private User testUser = new User(null, "nata", "Agent007", "nata@mail.ru",
            "nataliya", "bondarenko", Date.valueOf("1991-9-19"), roleUser);

    private void prepareDB() {
        roleDao.create(roleAdmin);
        roleDao.create(roleUser);
        Long id = userDao.create(userAdmin);
        userAdmin.setId(id);
        id = userDao.create(userUser);
        userUser.setId(id);
    }

    @Before
    public void setUp() throws IOException {
        ResourceConfig resourceConfig = new ResourceConfig(UsersResource.class);
        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
        server.start();
        target = ClientBuilder.newClient().target(BASE_URI + "/users");

        prepareDB();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testGetUserByLogin() {
        Invocation.Builder request = target.path("/admin").request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

        User user = response.readEntity(User.class);
        Assert.assertEquals(user, userAdmin);
    }

    @Test
    public void testGetUsers() throws IOException {
        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

        List<User> userList = response.readEntity(new GenericType<List<User>>() {
        });
        Assert.assertEquals(userList.size(), 2);
        Assert.assertTrue(userList.contains(userAdmin));
        Assert.assertTrue(userList.contains(userUser));
    }

    @Test
    public void testCreateUser() {
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(testUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertTrue(response.getStatus() == Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        userUser.setRole(roleAdmin);
        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(userUser, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
        User userByLogin = userDao.findByLogin(userUser.getLogin());
        Assert.assertEquals(userByLogin, userUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void testDelete() {
        Response response = target.path("/" + userUser.getId()).request().delete();
        Assert.assertTrue(response.getStatus() == Response.Status.NO_CONTENT.getStatusCode());

        userDao.findByLogin(userUser.getLogin());
    }
}
