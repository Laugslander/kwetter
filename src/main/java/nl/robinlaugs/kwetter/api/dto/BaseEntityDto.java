package nl.robinlaugs.kwetter.api.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author Robin Laugs
 */
@Getter
abstract class BaseEntityDto {

    private Long id;
    private LocalDateTime timestamp;

    BaseEntityDto(Long id, LocalDateTime timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

}
