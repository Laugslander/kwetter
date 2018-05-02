package nl.robinlaugs.kwetter.api.v2;

import nl.robinlaugs.kwetter.api.v2.dto.UserV2Dto;
import nl.robinlaugs.kwetter.service.UserService;

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
@Path("usersv2")
public class UserV2Resource extends BaseV2Resource {

    @Inject
    private UserService userService;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getUsers() {
        Collection<UserV2Dto> dto = userService.readAll()
                .stream()
                .map(u -> new UserV2Dto(u, uri.getBaseUri(), true))
                .collect(toList());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        try {
            UserV2Dto dto = new UserV2Dto(userService.read(id), uri.getBaseUri(), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionV2Dto(e);
        }
    }

}
