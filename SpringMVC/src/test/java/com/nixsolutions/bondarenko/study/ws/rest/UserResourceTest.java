package com.nixsolutions.bondarenko.study.ws.rest;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserRole;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = "classpath:application-context-web-service.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "classpath:/test_data/InitialDataSet.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL)
public class UserResourceTest {

    private static final URI BASE_URI = URI.create("http://10.10.35.56:8080/rest");

    private WebTarget target;

    private User user1;
    private User user2;
    private User newUser;

    public UserResourceTest() throws ParseException {
        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);
        target = client.target(BASE_URI).path("/users");

        Role roleAdmin = new Role(1L, UserRole.ADMIN.name());
        Role roleUser = new Role(2L, UserRole.USER.name());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        user1 = new User(1L, "yulya", "Pass123", "yulya@mail.ru",
                "yuliya", "bondarenko", formatter.parse("1993-01-10"), roleAdmin);
        user2 = new User(2L, "ivan", "Pass123", "ivan@mail.ru",
                "ivan", "grozniy", formatter.parse("1530-09-03"), roleUser);
        Date parsedDate = formatter.parse("1991-09-19");
        newUser = new User(5L, "nata", "Agent007", "nata@mail.ru",
                "nataliya", "bondarenko", parsedDate, roleUser);
    }

    @Test
    public void getUser() {
        Response response = target.path("/" + user1.getId()).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Assert.assertEquals(user1, response.readEntity(User.class));
    }

    @Test
    public void getUserNotExist() {
        Response response = target.path("/" + newUser.getId()).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getUsers() throws IOException {
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<User> users = response.readEntity(new GenericType<List<User>>() {
        });
        Assert.assertEquals(users.size(), 2);
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user2));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createUserPOST() throws IOException {
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        Assert.assertNotNull(response.readEntity(Long.class));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueLoginPOST() {
        newUser.setLogin(user1.getLogin());

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueEmailPOST() {
        newUser.setEmail(user1.getEmail());

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserInvalidPOST() {
        newUser.setPassword("invalid");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void updateUserPUT() {
        user1.setPassword("Agent007");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createUserPUT_id() {
        Response response = target.path("/" + newUser.getId()).request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        Assert.assertNotNull(response.readEntity(Long.class));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void updateUserPUT_id() {
        user1.setPassword("Agent007");
        Response response = target.path("/" + user1.getId()).request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserInvalidPUT() {
        user1.setPassword("invalid");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserNotUniqueEmailPUT() {
        user1.setEmail(user2.getEmail());
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml")
    public void deleteUser() {
        Response response = target.path("/" + user1.getId()).request().delete();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void deleteUserNotExisting() {
        Response response = target.path("/" + newUser.getId()).request().delete();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void badPathTest() {
        Response response = target.path("badpath").request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
