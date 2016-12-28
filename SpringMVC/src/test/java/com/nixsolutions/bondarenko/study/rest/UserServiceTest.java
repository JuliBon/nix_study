package com.nixsolutions.bondarenko.study.rest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.bondarenko.study.entity.User;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class UserServiceTest {
    @Test
    public void testGetPodcast() throws JsonGenerationException,
            JsonMappingException, IOException {

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);

        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget webTarget = client
                .target("http://localhost:8080/rest/UserService/users");

        Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = request.get();
        Assert.assertTrue(response.getStatus() == 200);
        System.out.println(response);

    }
}
