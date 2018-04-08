package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.MessageDto;
import nl.robinlaugs.kwetter.api.dto.UserDto;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.MessageService;
import nl.robinlaugs.kwetter.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
@Stateless
@Path("messages")
public class MessageResource extends BaseResource {

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
        try {
            MessageDto dto = new MessageDto(messageService.read(id), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response postMessage(Message data) {
        try {
            User author = userService.read(data.getAuthor().getId());
            Message message = new Message(data.getText());
            message.setAuthor(author);

            messageService.create(message);

            MessageDto dto = new MessageDto(message, true);

            return status(CREATED).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @GET
    @Path("{id}/likes")
    @Produces(APPLICATION_JSON)
    public Response getLikes(@PathParam("id") Long id) {
        try {
            Message message = messageService.read(id);

            Collection<UserDto> dto = message.getLikes().stream()
                    .map(u -> new UserDto(u, true))
                    .collect(toList());

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @POST
    @Path("{id}/likes/{likerId}")
    @Produces(APPLICATION_JSON)
    public Response like(@PathParam("id") Long id, @PathParam("likerId") Long likerId) {
        try {
            Message message = messageService.read(id);
            User liker = userService.read(likerId);

            if (!message.getLikes().contains(liker)) {
                messageService.like(message, liker);
            }

            MessageDto dto = new MessageDto(message, true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @DELETE
    @Path("{id}/likes/{likerId}")
    @Produces(APPLICATION_JSON)
    public Response unlike(@PathParam("id") Long id, @PathParam("likerId") Long likerId) {
        try {
            Message message = messageService.read(id);
            User liker = userService.read(likerId);

            if (message.getLikes().contains(liker)) {
                messageService.unlike(message, liker);
            }

            MessageDto dto = new MessageDto(message, true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @GET
    @Path("search/{searchString}")
    @Produces(APPLICATION_JSON)
    public Response searchMessages(@PathParam("searchString") String searchString) {
        Collection<MessageDto> dto = messageService.search(searchString).stream()
                .map(m -> new MessageDto(m, true))
                .collect(toList());

        return status(OK).entity(dto).build();
    }

}
