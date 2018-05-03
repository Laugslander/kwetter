package nl.robinlaugs.kwetter.api.v2.dto;

import lombok.Getter;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.Serializable;
import java.net.URI;

import static java.lang.String.format;
import static javax.json.Json.createObjectBuilder;

/**
 * @author Robin Laugs
 */
@Getter
public abstract class BaseEntityV2Dto implements Serializable {

    protected JsonObject data;
    protected transient Long id;
    protected transient URI uri;

    BaseEntityV2Dto(Long id, URI uri) {
        this.id = id;
        this.uri = uri;
    }

    JsonObject generateData(Class resource, boolean rich) {
        String name = resource.getSimpleName().toLowerCase();

        JsonObjectBuilder builder = createObjectBuilder()
                .add("type", name)
                .add("id", id)
                .add("link", format("%s%ssv2/%d", uri, name, id))
                .add("attributes", generateAttributes());

        if (rich) builder.add("relationships", generateRelationships());

        return builder.build();
    }

    abstract JsonObject getRelationship();

    abstract JsonObject generateAttributes();

    abstract JsonObject generateRelationships();

}
