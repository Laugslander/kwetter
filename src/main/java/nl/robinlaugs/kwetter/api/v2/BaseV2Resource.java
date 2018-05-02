package nl.robinlaugs.kwetter.api.v2;

import nl.robinlaugs.kwetter.api.v2.dto.ExceptionV2Dto;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.status;

/**
 * @author Robin Laugs
 */
public abstract class BaseV2Resource {

    @Context
    protected UriInfo uri;

    Response exceptionV2Dto(Exception exception) {
        ExceptionV2Dto dto = new ExceptionV2Dto(exception, BAD_REQUEST);

        return status(BAD_REQUEST).entity(dto).build();
    }

}
