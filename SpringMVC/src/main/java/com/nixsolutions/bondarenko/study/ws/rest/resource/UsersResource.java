package com.nixsolutions.bondarenko.study.ws.rest.resource;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.service.UserService;
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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        User user = userService.getUser(id);
        return Response.status(Response.Status.OK).entity(user).build();
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
        return Response.status(Response.Status.CREATED).entity("A new user has been created").build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(User user) {
        userService.updateUser(user);
        return Response.status(Response.Status.OK).entity("User has been updated").build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("id") Long id, User user) {
        if (userService.verifyUserExistence(id)) {
            userService.updateUser(user);
            return Response.status(Response.Status.OK).entity("User has been updated").build();
        } else {
            userService.createUser(user);
            return Response.status(Response.Status.CREATED).entity("A new user has been created").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
        return Response.status(Response.Status.OK).entity("User has been deleted").build();
    }
}
