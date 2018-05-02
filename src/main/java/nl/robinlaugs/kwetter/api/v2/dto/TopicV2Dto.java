package nl.robinlaugs.kwetter.api.v2.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.Topic;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.net.URI;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static javax.json.Json.createArrayBuilder;
import static javax.json.Json.createObjectBuilder;

/**
 * @author Robin Laugs
 */
@Getter
public class TopicV2Dto extends BaseEntityV2Dto {

    private transient Topic topic;

    public TopicV2Dto(Topic topic, URI uri, boolean fat) {
        super(topic.getId(), uri);

        this.topic = topic;

        data = generateData(Topic.class, fat);
    }

    @Override
    JsonObject getRelationship() {
        return createObjectBuilder()
                .add("id", id)
                .add("link", format("%stopicsv2/%d", uri, id))
                .add("attributes", generateAttributes())
                .build();
    }

    @Override
    JsonObject generateAttributes() {
        String name = topic.getName();

        JsonObjectBuilder builder = createObjectBuilder()
                .add("timestamp", topic.getTimestamp().toString());

        if (nonNull(name)) builder.add("name", name);

        return builder.build();
    }

    @Override
    JsonObject generateRelationships() {
        JsonArrayBuilder messagesBuilder = createArrayBuilder();

        topic.getMessages().stream()
                .map(m -> new MessageV2Dto(m, uri, false).getRelationship())
                .forEach(messagesBuilder::add);

        return createObjectBuilder()
                .add("messages", messagesBuilder.build())
                .build();
    }
}
