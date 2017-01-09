package com.nixsolutions.bondarenko.study.ws.rest.resource;

import com.nixsolutions.bondarenko.study.entity.User;
import com.nixsolutions.bondarenko.study.service.UserService;
import com.nixsolutions.bondarenko.study.ws.response.ResponseCode;
import com.nixsolutions.bondarenko.study.ws.response.UserCreateResponse;
import com.nixsolutions.bondarenko.study.ws.response.WebServiceResponse;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        Long id = userService.createUser(user);
        WebServiceResponse response = new UserCreateResponse(ResponseCode.OK, id, "A new user has been created");
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) {
        userService.updateUser(user);
        WebServiceResponse response = new WebServiceResponse(ResponseCode.OK, "User has been updated");
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, User user) {
        WebServiceResponse response;
        if (userService.verifyUserExistence(id)) {
            userService.updateUser(user);
            response = new WebServiceResponse(ResponseCode.OK,"User has been updated");
            return Response.status(Response.Status.OK).entity(response).build();
        } else {
            Long createdUserId = userService.createUser(user);
            response = new UserCreateResponse(ResponseCode.OK, createdUserId, "A new user has been created");
            return Response.status(Response.Status.CREATED).entity(response).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
        WebServiceResponse response = new WebServiceResponse(ResponseCode.OK, "User has been deleted");
        return Response.status(Response.Status.OK).entity(response).build();
    }
}
