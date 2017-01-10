package com.nixsolutions.bondarenko.study.ws.rest;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserRole;
import com.nixsolutions.bondarenko.study.ws.result.*;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
public class UserResourceTest {

    private static final URI BASE_URI = URI.create("http://localhost:8080/rest");
    Client client;
    private WebTarget target;

    private User user1;
    private User user2;
    private User newUser;

    public UserResourceTest() throws ParseException {
        client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);
        target = client.target(BASE_URI).path("/users");

        Role roleAdmin = new Role(1L, UserRole.ADMIN.name());
        Role roleUser = new Role(2L, UserRole.USER.name());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getDefault());

        user1 = new User(1L, "yulya", "12345", "yulya@mail.ru",
                "yuliya", "bondarenko", formatter.parse("1993-01-10"), roleAdmin);
        user2 = new User(2L, "ivan", "98765", "ivan@mail.ru",
                "ivan", "grozniy", formatter.parse("1530-09-03"), roleUser);
        Date parsedDate = formatter.parse("1991-09-19");
        newUser = new User(5L, "nata", "Agent007", "nata@mail.ru",
                "nataliya", "bondarenko", parsedDate, roleUser);
    }

    @Test
    public void getUser() {
        Response response = target.path("/" + user1.getId()).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        GetUserResult result = response.readEntity(GetUserResult.class);
        Assert.assertEquals(ResultCode.OK, result.getResultCode());
        Assert.assertEquals(user1, result.getUser());
    }

    @Test
    public void getUserNotExist() {
        Response response = target.path("/" + newUser.getId()).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

        WebServiceResult result = response.readEntity(WebServiceResult.class);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.USER_NOT_FOUND, result.getErrorCode());
    }

    @Test
    public void getUsers() throws IOException {
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        GetUsersResult result = response.readEntity(GetUsersResult.class);
        Assert.assertEquals(ResultCode.OK, result.getResultCode());

        List<User> users = result.getUserList();
        Assert.assertEquals(users.size(), 2);
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user2));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml")
    public void createUserPOST() {
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        UserCreateResult createResult = response.readEntity(UserCreateResult.class);
        Assert.assertEquals(ResultCode.OK, createResult.getResultCode());
        Assert.assertTrue(createResult.getId().equals(3L));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueLoginPOST() {
        newUser.setLogin(user1.getLogin());

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        WebServiceResult result = response.readEntity(WebServiceResult.class);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.NOT_UNIQUE_LOGIN, result.getErrorCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueEmailPOST() {
        newUser.setEmail(user1.getEmail());

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        WebServiceResult result = response.readEntity(WebServiceResult.class);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.NOT_UNIQUE_EMAIL, result.getErrorCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserBadPOST() {
        newUser.setPassword("invalid");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        WebServiceResult result = response.readEntity(WebServiceResult.class);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.INVALID_USER, result.getErrorCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateUserPUT() {
        user1.setPassword("Agent007");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Assert.assertEquals(ResultCode.OK, response.readEntity(WebServiceResult.class).getResultCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createUserPUT_id() {
        Response response = target.path("/3").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        UserCreateResult createResult = response.readEntity(UserCreateResult.class);
        Assert.assertEquals(ResultCode.OK, createResult.getResultCode());
        Assert.assertTrue(createResult.getId().equals(3L));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void updateUserPUT_id() {
        user1.setPassword("Agent007");
        Response response = target.path("/1").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Assert.assertEquals(ResultCode.OK, response.readEntity(WebServiceResult.class).getResultCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserInvalidPUT() {
        user1.setPassword("invalid");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        WebServiceResult result = response.readEntity(WebServiceResult.class);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.INVALID_USER, result.getErrorCode());

    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserNotUniqueEmailPUT() {
        user1.setEmail(user2.getEmail());
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        WebServiceResult result = response.readEntity(WebServiceResult.class);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.NOT_UNIQUE_EMAIL, result.getErrorCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml")
    public void deleteUser() {
        Response response = target.path("/" + user1.getId()).request().delete();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Assert.assertEquals(ResultCode.OK, response.readEntity(WebServiceResult.class).getResultCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void deleteUserNotExisting() {
        Response response = target.path("/999").request().delete();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

        WebServiceResult result = response.readEntity(WebServiceResult.class);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.USER_NOT_FOUND, result.getErrorCode());
    }

    @Test
    public void badPathTest() {
        Response response = target.path("badpath").request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

        WebServiceResult result = response.readEntity(WebServiceResult.class);
        Assert.assertEquals(ResultCode.ERROR, result.getResultCode());
        Assert.assertEquals(ErrorCode.WEB_APPLICATION_ERROR, result.getErrorCode());
    }
}
