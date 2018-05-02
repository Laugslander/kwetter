package nl.robinlaugs.kwetter.api.v2.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.Message;

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
public class MessageV2Dto extends BaseEntityV2Dto {

    private transient Message message;

    public MessageV2Dto(Message message, URI uri, boolean fat) {
        super(message.getId(), uri);

        this.message = message;

        data = generateData(Message.class, fat);
    }

    @Override
    JsonObject getRelationship() {
        return createObjectBuilder()
                .add("id", id)
                .add("link", format("%smessagesv2/%d", uri, id))
                .add("attributes", generateAttributes())
                .build();
    }

    @Override
    JsonObject generateAttributes() {
        String text = message.getText();

        JsonObjectBuilder builder = createObjectBuilder()
                .add("timestamp", message.getTimestamp().toString());

        if (nonNull(text)) builder.add("text", text);

        return builder.build();
    }

    @Override
    JsonObject generateRelationships() {
        UserV2Dto author = new UserV2Dto(message.getAuthor(), uri, false);

        JsonArrayBuilder likesBuilder = createArrayBuilder();
        JsonArrayBuilder mentionsBuilder = createArrayBuilder();
        JsonArrayBuilder topicsBuilder = createArrayBuilder();

        message.getLikes().stream()
                .map(l -> new UserV2Dto(l, uri, false).getRelationship())
                .forEach(likesBuilder::add);

        message.getMentions().stream()
                .map(m -> new UserV2Dto(m, uri, false).getRelationship())
                .forEach(mentionsBuilder::add);

        message.getTopics().stream()
                .map(t -> new TopicV2Dto(t, uri, false).getRelationship())
                .forEach(topicsBuilder::add);

        return createObjectBuilder()
                .add("author", author.getRelationship())
                .add("likes", likesBuilder.build())
                .add("mentions", mentionsBuilder.build())
                .add("topics", topicsBuilder.build())
                .build();
    }

}
