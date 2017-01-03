package com.nixsolutions.bondarenko.study.rest;

import com.nixsolutions.bondarenko.study.entity.Role;
import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.rest.resource.UsersResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.sql.Date;


public class UserResourceTest {
    private static final URI BASE_URI = URI.create("http://localhost:8080/rest/users");
    private HttpServer server;
    private WebTarget target;

    private User testUser = new User(null, "nata", "Agent007", "nata@mail.ru",
            "nataliya", "bondarenko", Date.valueOf("1991-9-19"), new Role(2L, "USER"));

    @Before
    public void setUp() throws IOException {
/*        ResourceConfig resourceConfig = new ResourceConfig(UsersResource.class );
        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
        server.start();*/

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);
        Client client = ClientBuilder.newClient(clientConfig);
        target = client.target(BASE_URI);
    }

/*    @After
    public void tearDown() throws Exception {
        server.stop();
    }*/


    @Test
    public void testGetUserByLogin() {
        Invocation.Builder request = target.path("/yulya").request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        Assert.assertTrue(response.getStatus() == 200);
    }

    @Test
    public void testGetUsers() throws IOException {
        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);
        Response response = request.get();

        Assert.assertTrue(response.getStatus() == 200);
    }

    @Test
    public void testCreateUser() {
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(testUser, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertTrue(response.getStatus() == 201);
    }

    @Test
    public void testUpdateUser() {
        testUser.setId(240L);
        testUser.setRole(new Role(1L, "ADMIN"));
        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(testUser, MediaType.APPLICATION_JSON), Response.class);
        System.out.println(response.getStatus());
        Assert.assertTrue(response.getStatus() == 200);
    }

    @Test
    public void testDelete() {
        Response response = target.path("/197").request().delete();
        Assert.assertTrue(response.getStatus() == 204);
    }
}
