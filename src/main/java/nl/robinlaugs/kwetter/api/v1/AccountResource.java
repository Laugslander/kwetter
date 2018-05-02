package nl.robinlaugs.kwetter.api.v1;

import io.jsonwebtoken.Jwts;
import nl.robinlaugs.kwetter.api.v1.dto.AccountDto;
import nl.robinlaugs.kwetter.api.v1.dto.CredentialDto;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.service.AccountService;
import nl.robinlaugs.kwetter.service.auth.KeyService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.util.stream.Collectors.toList;
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
    private AccountService accountService;

    @Inject
    private KeyService keyService;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getAccounts() {
        Collection<AccountDto> dto = accountService.readAll().stream()
                .map(a -> new AccountDto(a, true))
                .collect(toList());

        return status(OK).entity(dto).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getAccount(@PathParam("id") Long id) {
        try {
            AccountDto dto = new AccountDto(accountService.read(id), true);

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
            accountService.create(account);

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
            AccountDto dto = new AccountDto(accountService.update(id, update), true);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

    @POST
    @Path("/login")
    @Consumes(APPLICATION_JSON)
    public Response authenticate(Account account) {
        try {
            String username = account.getUsername();
            String password = account.getPassword();

            account = accountService.read(username, password);

            String token = Jwts.builder()
                    .setSubject(account.getUsername())
                    .setIssuedAt(new Date())
                    .signWith(HS512, keyService.generate())
                    .compact();

            CredentialDto dto = new CredentialDto(account.getUser().getId(), token);

            return status(OK).entity(dto).build();
        } catch (Exception e) {
            return exceptionDto(e);
        }
    }

}
