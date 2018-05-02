package nl.robinlaugs.kwetter.api.v1.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.Topic;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

/**
 * @author Robin Laugs
 */
@Getter
public class TopicDto extends BaseEntityDto {

    private String name;

    private Collection<MessageDto> messages;

    TopicDto(Topic topic) {
        super(topic.getId(), topic.getTimestamp());

        name = topic.getName();
    }

    public TopicDto(Topic topic, boolean fat) {
        this(topic);

        if (fat) {
            messages = topic.getMessages().stream().map(MessageDto::new).collect(toSet());
        }
    }

}
