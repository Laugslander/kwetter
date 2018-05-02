package nl.robinlaugs.kwetter.api.v2.dto;

import lombok.Getter;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.io.Serializable;

import static javax.json.Json.createObjectBuilder;

/**
 * @author Robin Laugs
 */
@Getter
public class ExceptionV2Dto implements Serializable {

    private JsonObject error;

    private transient Exception exception;
    private transient Response.Status status;

    public ExceptionV2Dto(Exception exception, Response.Status status) {
        this.exception = exception;
        this.status = status;

        error = generateError();
    }

    private JsonObject generateError() {
        return createObjectBuilder()
                .add("status", status.getStatusCode())
                .add("title", status.getReasonPhrase())
                .add("detail", exception.getMessage())
                .build();
    }

}
