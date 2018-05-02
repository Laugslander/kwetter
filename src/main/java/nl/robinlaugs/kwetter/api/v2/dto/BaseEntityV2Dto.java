package nl.robinlaugs.kwetter.api.v2.dto;

import lombok.Getter;

import javax.json.JsonObject;
import java.io.Serializable;
import java.net.URI;

/**
 * @author Robin Laugs
 */
@Getter
public class BaseEntityV2Dto implements Serializable {

    protected JsonObject data;
    protected transient Long id;
    protected transient URI uri;

    BaseEntityV2Dto(Long id, URI uri) {
        this.id = id;
        this.uri = uri;
    }

}
