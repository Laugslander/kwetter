package nl.robinlaugs.kwetter.api.v2.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.User;

import javax.json.JsonObject;
import java.net.URI;

import static java.lang.String.format;
import static javax.json.Json.createObjectBuilder;

/**
 * @author Robin Laugs
 */
@Getter
public class UserV2Dto extends BaseEntityV2Dto {

    public UserV2Dto(User user, URI uri) {
        super(user.getId(), uri);
    }

    public JsonObject getRelation() {
        JsonObject related = createObjectBuilder()
                .add("related", format("%susersv2/%d", uri, id))
                .build();

        return createObjectBuilder()
                .add("author", related)
                .build();
    }

}
