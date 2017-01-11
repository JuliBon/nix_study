package com.nixsolutions.bondarenko.study.ws.rest.resource;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.service.UserService;
import com.nixsolutions.bondarenko.study.ws.result.*;
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
        GetUserResult getUserResult = new GetUserResult(ResultCode.OK, user);
        return Response.status(Response.Status.OK).entity(getUserResult).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = userService.getUsers();
        GetUsersResult getUsersResult = new GetUsersResult(ResultCode.OK, users);
        return Response.status(Response.Status.OK).entity(getUsersResult).build();

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        Long id = userService.createUser(user);
        WebServiceResult result = new UserCreateResult(ResultCode.OK, id, "A new user has been created");
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) {
        userService.updateUser(user);
        WebServiceResult result = new WebServiceResult(ResultCode.OK, "User has been updated");
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, User user) {
        WebServiceResult result;
        if (userService.verifyUserExistence(id)) {
            userService.updateUser(user);
            result = new WebServiceResult(ResultCode.OK, "User has been updated");
            return Response.status(Response.Status.OK).entity(result).build();
        } else {
            Long createdUserId = userService.createUser(user);
            result = new UserCreateResult(ResultCode.OK, createdUserId, "A new user has been created");
            return Response.status(Response.Status.CREATED).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
        WebServiceResult result = new WebServiceResult(ResultCode.OK, "User has been deleted");
        return Response.status(Response.Status.OK).entity(result).build();
    }
}