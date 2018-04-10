package nl.robinlaugs.kwetter.api.auth;

import lombok.extern.java.Log;
import nl.robinlaugs.kwetter.service.auth.KeyService;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.security.Key;

import static io.jsonwebtoken.Jwts.parser;
import static javax.ws.rs.Priorities.AUTHENTICATION;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
@Provider
@JwtTokenRequired
@Priority(AUTHENTICATION)
@Log
public class JwtTokenRequiredFilter implements ContainerRequestFilter {

    @Inject
    private KeyService service;

    @Override
    public void filter(ContainerRequestContext context) {
        try {
            String header = context.getHeaderString(AUTHORIZATION);
            String token = header.substring("Bearer".length()).trim();

            Key key = service.generate();
            parser().setSigningKey(key).parseClaimsJws(token);
        } catch (Exception e) {
            context.abortWith(status(UNAUTHORIZED).build());
        }
    }
}
