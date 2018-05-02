package nl.robinlaugs.kwetter.api.v2;

import nl.robinlaugs.kwetter.api.v2.dto.AccountV2Dto;
import nl.robinlaugs.kwetter.service.AccountService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
@Stateless
@Path("accountsv2")
public class AccountV2Resource extends BaseV2Resource {

    @Context
    private UriInfo uri;

    @Inject
    private AccountService accountService;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getAccounts() {
        Collection<AccountV2Dto> dto = accountService.readAll()
                .stream()
                .map(a -> new AccountV2Dto(a, uri.getBaseUri()))
                .collect(toList());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getAccount(@PathParam("id") Long id) {
        try {
            AccountV2Dto dto = new AccountV2Dto(accountService.read(id), uri.getBaseUri());

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionV2Dto(e);
        }
    }

}
