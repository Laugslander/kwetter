package nl.robinlaugs.kwetter.api.v1;

import nl.robinlaugs.kwetter.api.v1.dto.MessageDto;
import nl.robinlaugs.kwetter.api.v1.dto.TopicDto;
import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.service.TopicService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
@Stateless
@Path("topics")
public class TopicResource extends BaseResource {

    private static final int NUMBER_OF_TRENDING_TOPICS = 5;

    @Inject
    private TopicService service;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getTopics() {
        Collection<TopicDto> dto = service.readAll().stream()
                .map(t -> new TopicDto(t, true))
                .collect(toSet());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getTopic(@PathParam("id") Long id) {
        try {
            TopicDto dto = new TopicDto(service.read(id), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @GET
    @Path("{id}/messages")
    @Produces(APPLICATION_JSON)
    public Response topicMessages(@PathParam("id") Long id) {
        try {
            Topic topic = service.read(id);

            Collection<MessageDto> dto = topic.getMessages().stream()
                    .sorted()
                    .map(m -> new MessageDto(m, true))
                    .collect(toList());

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @GET
    @Path("trending")
    @Produces(APPLICATION_JSON)
    public Response trending() {
        Collection<TopicDto> dto = service.readTrendingTopics(now().minusWeeks(1), NUMBER_OF_TRENDING_TOPICS).stream()
                .map(t -> new TopicDto(t, true))
                .collect(toList());

        return status(OK).entity(dto).build();
    }

}
