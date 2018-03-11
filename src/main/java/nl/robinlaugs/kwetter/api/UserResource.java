package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.ExceptionDto;
import nl.robinlaugs.kwetter.api.dto.MessageDto;
import nl.robinlaugs.kwetter.api.dto.UserDto;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
@Stateless
@Path("users")
public class UserResource {

    private static final int NUMBER_OF_PERSONAL_MESSAGES = 10;

    @Inject
    private UserService service;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getUsers() {
        Collection<UserDto> dto = service.readAll().stream()
                .map(u -> new UserDto(u, true))
                .collect(toSet());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        UserDto dto = new UserDto(service.read(id), true);

        return status(OK).entity(dto).build();
    }

    @PATCH
    @Path("{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response patchUser(@PathParam("id") Long id, User user) {
        try {
            UserDto dto = new UserDto(service.update(id, user), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            ExceptionDto dto = new ExceptionDto(e);

            return status(BAD_REQUEST).entity(dto).build();
        }
    }

    @POST
    @Path("{id}/followers/{followerId}")
    @Produces(APPLICATION_JSON)
    public Response follow(@PathParam("id") Long id, @PathParam("followerId") Long followerId) {
        User user = service.read(id);
        User follower = service.read(followerId);

        if (!user.getFollowers().contains(follower)) {
            service.follow(user, follower);
        }

        UserDto dto = new UserDto(service.read(id), true);

        return status(OK).entity(dto).build();
    }

    @DELETE
    @Path("{id}/followers/{followerId}")
    @Produces(APPLICATION_JSON)
    public Response unfollow(@PathParam("id") Long id, @PathParam("followerId") Long followerId) {
        User user = service.read(id);
        User follower = service.read(followerId);

        if (user.getFollowers().contains(follower)) {
            service.unfollow(user, follower);
        }

        UserDto dto = new UserDto(service.read(id), true);

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}/messagesTimeline")
    @Produces(APPLICATION_JSON)
    public Response timelineMessages(@PathParam("id") Long id) {
        User user = service.read(id);

        Collection<MessageDto> dto = service.readAll(user).stream()
                .map(m -> new MessageDto(m, true))
                .collect(toSet());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}/messagesPersonal")
    @Produces(APPLICATION_JSON)
    public Response personalMessages(@PathParam("id") Long id) {
        User user = service.read(id);

        Collection<MessageDto> dto = service.readOwn(user, NUMBER_OF_PERSONAL_MESSAGES).stream()
                .map(m -> new MessageDto(m, true))
                .collect(toSet());

        return status(OK).entity(dto).build();
    }

}
