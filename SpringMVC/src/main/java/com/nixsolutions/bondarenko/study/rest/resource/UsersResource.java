package com.nixsolutions.bondarenko.study.rest.resource;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/users")
public class UsersResource {

    @Autowired
    private UserService userService;

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("login") String login) {
        return userService.getUser(login);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
    }
}
