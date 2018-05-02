package nl.robinlaugs.kwetter.api.v1.dto;

import lombok.Getter;

/**
 * @author Robin Laugs
 */
@Getter
public class ExceptionDto {

    private String message;

    public ExceptionDto(Exception exception) {
        this.message = exception.getMessage();
    }

}
