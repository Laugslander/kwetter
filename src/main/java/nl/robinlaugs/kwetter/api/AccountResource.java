package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.AccountDto;
import nl.robinlaugs.kwetter.api.dto.ExceptionDto;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.service.AccountService;

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
@Path("accounts")
public class AccountResource {

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
        AccountDto dto = new AccountDto(service.read(id), true);

        return status(OK).entity(dto).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response postAccount(Account account) {
        try {
            service.create(account);

            AccountDto dto = new AccountDto(service.read(account.getId()), true);

            return status(CREATED).entity(dto).build();
        } catch (Exception e) {
            return status(BAD_REQUEST).entity(new ExceptionDto(e)).build();
        }
    }

    @PATCH
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response patchAccount(Account account) {
        try {
            service.update(account);

            AccountDto dto = new AccountDto(service.read(account.getId()), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            ExceptionDto dto = new ExceptionDto(e);

            return status(BAD_REQUEST).entity(dto).build();
        }
    }

}