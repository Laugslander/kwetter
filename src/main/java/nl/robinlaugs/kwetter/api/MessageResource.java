package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.ExceptionDto;
import nl.robinlaugs.kwetter.api.dto.MessageDto;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.MessageService;
import nl.robinlaugs.kwetter.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
@Stateless
@Path("messages")
public class MessageResource {

    @Inject
    private MessageService messageService;

    @Inject
    private UserService userService;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getMessages() {
        Collection<MessageDto> dto = messageService.readAll().stream()
                .map(m -> new MessageDto(m, true))
                .collect(toSet());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getMessage(@PathParam("id") Long id) {
        MessageDto dto = new MessageDto(messageService.read(id), true);

        return status(OK).entity(dto).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response postMessage(Message message) {
        try {
            messageService.create(message);

            MessageDto dto = new MessageDto(message, true);

            return status(CREATED).entity(dto).build();
        } catch (Exception e) {
            ExceptionDto dto = new ExceptionDto(e);

            return status(BAD_REQUEST).entity(dto).build();
        }
    }

    @POST
    @Path("{id}/likes/{likerId}")
    @Produces(APPLICATION_JSON)
    public Response like(@PathParam("id") Long id, @PathParam("likerId") Long likerId) {
        Message message = messageService.read(id);
        User liker = userService.read(likerId);

        if (!message.getLikes().contains(liker)) {
            messageService.like(message, liker);
        }

        MessageDto dto = new MessageDto(message, true);

        return status(OK).entity(dto).build();
    }

    @DELETE
    @Path("{id}/likes/{likerId}")
    @Produces(APPLICATION_JSON)
    public Response unlike(@PathParam("id") Long id, @PathParam("likerId") Long likerId) {
        Message message = messageService.read(id);
        User liker = userService.read(likerId);

        if (message.getLikes().contains(liker)) {
            messageService.unlike(message, liker);
        }

        MessageDto dto = new MessageDto(message, true);

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("search/{searchString}")
    @Produces(APPLICATION_JSON)
    public Response searchMessages(@PathParam("searchString") String searchString) {
        Collection<MessageDto> dto = messageService.search(searchString).stream()
                .map(m -> new MessageDto(m, true))
                .collect(toSet());

        return status(OK).entity(dto).build();
    }

}
