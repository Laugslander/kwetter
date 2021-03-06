package nl.robinlaugs.kwetter.api.v2;

import nl.robinlaugs.kwetter.api.v2.dto.TopicV2Dto;
import nl.robinlaugs.kwetter.service.TopicService;

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
@Path("topicsv2")
public class TopicV2Resource extends BaseV2Resource {

    @Inject
    private TopicService topicService;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getTopics() {
        Collection<TopicV2Dto> dto = topicService.readAll()
                .stream()
                .map(t -> new TopicV2Dto(t, uri.getBaseUri(), true))
                .collect(toList());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getTopic(@PathParam("id") Long id) {
        try {
            TopicV2Dto dto = new TopicV2Dto(topicService.read(id), uri.getBaseUri(), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionV2Dto(e);
        }
    }

}
