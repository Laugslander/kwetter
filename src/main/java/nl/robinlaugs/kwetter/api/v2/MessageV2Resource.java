package nl.robinlaugs.kwetter.api.v2;

import nl.robinlaugs.kwetter.api.v2.dto.MessageV2Dto;
import nl.robinlaugs.kwetter.service.MessageService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
@Stateless
@Path("messagesv2")
public class MessageV2Resource extends BaseV2Resource {

    @Inject
    private MessageService messageService;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getMessages() {
        Collection<MessageV2Dto> dto = messageService.readAll()
                .stream()
                .map(m -> new MessageV2Dto(m, uri.getBaseUri(), true))
                .collect(toList());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getMessage(@PathParam("id") Long id) {
        try {
            MessageV2Dto dto = new MessageV2Dto(messageService.read(id), uri.getBaseUri(), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionV2Dto(e);
        }
    }

}
