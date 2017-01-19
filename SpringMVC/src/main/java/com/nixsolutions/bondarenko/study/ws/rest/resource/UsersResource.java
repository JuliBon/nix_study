package com.nixsolutions.bondarenko.study.ws.rest.resource;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.exception.NotUniqueEmailException;
import com.nixsolutions.bondarenko.study.exception.NotUniqueLoginException;
import com.nixsolutions.bondarenko.study.exception.UserNotFoundException;
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
    public Response getUser(@PathParam("id") Long id) throws UserNotFoundException {
        User user = userService.getUser(id);
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = userService.getUsers();
        return Response.status(Response.Status.OK).entity(users).build();

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) throws NotUniqueLoginException, NotUniqueEmailException {
        Long id = userService.createUser(user);
        return Response.status(Response.Status.CREATED).entity(id).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) throws NotUniqueEmailException {
        userService.updateUser(user);
        return Response.status(Response.Status.OK).entity("User has been updated").build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, User user) throws NotUniqueEmailException, NotUniqueLoginException {
        if (userService.verifyUserExistence(id)) {
            userService.updateUser(user);
            return Response.status(Response.Status.OK).build();
        } else {
            Long createdUserId = userService.createUser(user);
            return Response.status(Response.Status.CREATED).entity(createdUserId).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
        return Response.status(Response.Status.OK).build();
    }
}