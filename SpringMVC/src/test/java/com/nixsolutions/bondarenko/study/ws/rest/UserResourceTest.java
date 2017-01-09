package com.nixsolutions.bondarenko.study.ws.rest;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.entity.UserRole;
import com.nixsolutions.bondarenko.study.ws.response.ResponseCode;
import com.nixsolutions.bondarenko.study.ws.response.UserCreateResponse;
import com.nixsolutions.bondarenko.study.ws.response.WebServiceResponse;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

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
import java.util.List;
import java.util.TimeZone;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:/test_data/InitialDataSet.xml")
public class UserResourceTest {

    private HttpServer server;
    private WebTarget target;
    private static final URI BASE_URI = URI.create("http://localhost:8081/rest");

    private User user1;
    private User user2;
    private User newUser;

    public UserResourceTest() throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new JerseyAppConfig());

        Role roleAdmin = new Role(1L, UserRole.ADMIN.name());
        Role roleUser = new Role(2L, UserRole.USER.name());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        user1 = new User(1L, "yulya", "12345", "yulya@mail.ru",
                "yuliya", "bondarenko", formatter.parse("1993-01-10"), roleAdmin);
        user2 = new User(2L, "ivan", "98765", "ivan@mail.ru",
                "ivan", "grozniy", formatter.parse("1530-09-03"), roleUser);
        newUser = new User(5L, "nata", "Agent007", "nata@mail.ru",
                "nataliya", "bondarenko", formatter.parse("1991-09-19"), roleUser);
    }

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
    public void getUser() {
        Response response = target.path("/" + user1.getId()).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        User user = response.readEntity(User.class);
        Assert.assertEquals(user, user1);
    }

    @Test
    public void getUserNotExist() {
        Response response = target.path("/" + newUser.getId()).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        Assert.assertEquals(ResponseCode.USER_NOT_FOUND, response.readEntity(WebServiceResponse.class).getCode());
    }

    @Test
    public void getUsers() throws IOException {
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        List<User> users = response.readEntity(new GenericType<List<User>>() {
        });
        Assert.assertEquals(users.size(), 2);
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user2));
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml")
    public void createUserPOST() {
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Assert.assertTrue(response.readEntity(UserCreateResponse.class).getId().equals(3L));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueLoginPOST() {
        newUser.setLogin(user1.getLogin());

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Assert.assertEquals(ResponseCode.NOT_UNIQUE_LOGIN, response.readEntity(WebServiceResponse.class).getCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserNotUniqueEmailPOST() {
        newUser.setEmail(user1.getEmail());

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Assert.assertEquals(ResponseCode.NOT_UNIQUE_EMAIL, response.readEntity(WebServiceResponse.class).getCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void createUserBadPOST() {
        newUser.setPassword("invalid");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Assert.assertEquals(ResponseCode.INVALID_USER, response.readEntity(WebServiceResponse.class).getCode());
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
    @ExpectedDatabase(value = "/test_data/UserCreateExpectedDataSet.xml")
    public void createUserPUT_id() {
        Response response = target.path("/3").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Assert.assertTrue(response.readEntity(UserCreateResponse.class).getId().equals(3L));
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserUpdateExpectedDataSet.xml")
    public void updateUserPUT_id() {
        user1.setPassword("Agent007");
        Response response = target.path("/1").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(ResponseCode.OK, response.readEntity(WebServiceResponse.class).getCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserInvalidPUT() {
        user1.setPassword("invalid");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Assert.assertEquals(ResponseCode.INVALID_USER, response.readEntity(WebServiceResponse.class).getCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void updateUserNotUniqueEmailPUT() {
        user1.setEmail(user2.getEmail());
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user1, MediaType.APPLICATION_JSON), Response.class);

        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Assert.assertEquals(ResponseCode.NOT_UNIQUE_EMAIL, response.readEntity(WebServiceResponse.class).getCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/UserRemoveExpectedDataSet.xml")
    public void deleteUser() {
        Response response = target.path("/" + user1.getId()).request().delete();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(ResponseCode.OK, response.readEntity(WebServiceResponse.class).getCode());
    }

    @Test
    @ExpectedDatabase(value = "/test_data/InitialDataSet.xml")
    public void deleteUserNotExisting() {
        Response response = target.path("/999").request().delete();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        Assert.assertEquals(ResponseCode.USER_NOT_FOUND, response.readEntity(WebServiceResponse.class).getCode());
    }

    @Test
    public void badPathTest() {
        Response response2 = target.path("badpath").request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response2.getStatus());
    }
}
