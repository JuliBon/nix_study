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
        try {
            userService.createUser(user);
            return Response.status(Response.Status.CREATED).entity("A new user has been created").build();
        } catch (Exception exception) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User has not been created. " + exception.getMessage()).build();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(User user) {
        try {
            userService.updateUser(user);
            return Response.status(Response.Status.OK).entity("User has been updated").build();
        } catch (Exception exception) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User has not been updated. " + exception.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("id") Long id, User user) {
        try {
            if (userService.verifyUserExistence(id)) {
                userService.updateUser(user);
                return Response.status(Response.Status.OK).entity("User has been updated").build();
            } else {
                userService.createUser(user);
                return Response.status(Response.Status.CREATED).entity("A new user has been created").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Can't create or update user. " + exception.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        if (userService.verifyUserExistence(id)) {
            userService.deleteUser(id);
            return Response.status(Response.Status.NO_CONTENT).entity("User has been deleted").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("User with id" + id + "does not exist and can't be deleted").build();
        }
    }
}
