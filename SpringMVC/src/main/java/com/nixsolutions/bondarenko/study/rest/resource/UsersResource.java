package com.nixsolutions.bondarenko.study.rest.resource;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UsersResource {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(User user) {
        userService.createUser(user);
        return Response.status(Response.Status.CREATED) // 201
                .entity("A new user has been created").build();

    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(User user) {
        userService.updateUser(user);
        return Response.status(Response.Status.OK)
                .entity("User has been updated").build();

    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
    }
}
