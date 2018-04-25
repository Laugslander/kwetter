package nl.robinlaugs.kwetter.api.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.Message;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

/**
 * @author Robin Laugs
 */
@Getter
public class MessageDto extends BaseEntityDto {

    private String text;

    private UserDto author;
    private Collection<UserDto> likes;
    private Collection<UserDto> mentions;
    private Collection<TopicDto> topics;

    public MessageDto(Message message) {
        super(message.getId(), message.getTimestamp());

        text = message.getText();
    }

    public MessageDto(Message message, boolean fat) {
        this(message);

        if (fat) {
            author = new UserDto(message.getAuthor());
            likes = message.getLikes().stream().map(UserDto::new).collect(toSet());
            mentions = message.getMentions().stream().map(UserDto::new).collect(toSet());
            topics = message.getTopics().stream().map(TopicDto::new).collect(toSet());
        }
    }
}
