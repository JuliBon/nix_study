package com.nixsolutions.bondarenko.study.rest;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserRole;
import com.nixsolutions.bondarenko.study.rest.resource.UsersResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.sql.Date;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
public class UserResourceTest {

    private HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new ResourceConfig(UsersResource.class));;
    private WebTarget target;

    private static final URI BASE_URI = URI.create("http://localhost:8081/rest");

    private Role roleAdmin = new Role(1L, UserRole.ADMIN.name());
    private Role roleUser = new Role(2L, UserRole.USER.name());
    private User user1 = new User(1L, "yulya", "12345", "yulya@mail.ru",
            "yuliya", "bondarenko", Date.valueOf("1993-01-10"), roleAdmin);
    private User newUser = new User(5L, "nata", "Agent007", "nata@mail.ru",
            "nataliya", "bondarenko", Date.valueOf("1991-9-19"), roleUser);

    @Before
    public void setUp() throws IOException {
        server.start();
        target = ClientBuilder.newClient().target(BASE_URI + "/users");
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getUserByLogin() {
        Response response = target.path("/yulya").request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

        User user = response.readEntity(User.class);
        Assert.assertEquals(user, user1);
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void getUsers() throws IOException {
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml")
    public void createUserPOST() {
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueLoginPOST() {
        newUser.setLogin(user1.getLogin());

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueEmailPOST() {
        newUser.setEmail(user1.getEmail());

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void updateUserPUT() {
        user1.setPassword("Agent007");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml")
    public void createUserPUT_id() {
        Response response = target.path("/3").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void updateUserPUT_id() {
        user1.setPassword("Agent007");
        Response response = target.path("/1").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserBadPUT() {
        user1.setPassword("invalid");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserNotUniqueEmailPUT() {
        user1.setEmail("ivan@mail.ru");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml")
    public void deleteUser() {
        Response response = target.path("/1").request().delete();
        Assert.assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void deleteUserNotExisting() {
        Response response = target.path("/999").request().delete();
        Assert.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }
}
