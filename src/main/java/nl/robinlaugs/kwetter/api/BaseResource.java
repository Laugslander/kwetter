package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.ExceptionDto;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
public abstract class BaseResource {

    Response exceptionDto(Exception exception) {
        ExceptionDto dto = new ExceptionDto(exception);

        return status(BAD_REQUEST).entity(dto).build();
    }

}
