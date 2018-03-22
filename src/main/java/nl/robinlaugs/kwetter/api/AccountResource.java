package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.AccountDto;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.service.AccountService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
@Stateless
@Path("accounts")
public class AccountResource extends BaseResource {

    @Inject
    private AccountService service;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getAccounts() {
        Collection<AccountDto> dto = service.readAll().stream()
                .map(a -> new AccountDto(a, true))
                .collect(toSet());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getAccount(@PathParam("id") Long id) {
        try {
            AccountDto dto = new AccountDto(service.read(id), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response postAccount(Account account) {
        try {
            service.create(account);

            AccountDto dto = new AccountDto(account, true);

            return status(CREATED).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @PATCH
    @Path("{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response patchAccount(@PathParam("id") Long id, Account update) {
        try {
            AccountDto dto = new AccountDto(service.update(id, update), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

}
