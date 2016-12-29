package com.nixsolutions.bondarenko.study.rest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nixsolutions.bondarenko.study.entity.User;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


public class UserServiceTest {
    private static final String BASE_URI = "http://localhost:8080/rest/";
    private Client client;

    @Before
    public void setUp(){
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);
        client = ClientBuilder.newClient(clientConfig);
    }

    @Test
    public void testGetByLogin(){
        WebTarget webTarget = client.target(BASE_URI + "user/yulya");

        Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = request.get();

        Assert.assertTrue(response.getStatus() == 200);
        User user = response.readEntity(User.class);
    }

    @Test
    public void testGetById(){
        WebTarget webTarget = client.target(BASE_URI + "user/196");

        Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = request.get();

        Assert.assertTrue(response.getStatus() == 200);
        User user = response.readEntity(User.class);
    }

    @Test
    public void testGetUsers() throws JsonGenerationException,
            JsonMappingException, IOException {
        WebTarget webTarget = client.target(BASE_URI + "users");

        Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = request.get();

        Assert.assertTrue(response.getStatus() == 200);
        List<User> userList = response.readEntity(new GenericType<List<User>>() {
        });
    }

    @Test
    public void testDelete(){
        WebTarget service = client.target(BASE_URI);

        Response response = service.path("delete/196").request().delete();
        Assert.assertTrue(response.getStatus() == 200);
    }

}
