package nl.robinlaugs.kwetter.service;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.exception.InputConstraintViolationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;
import static nl.robinlaugs.kwetter.domain.Message.MAX_TEXT_CHARACTERS;

/**
 * @author Robin Laugs
 */
@Stateless
@Getter
public class KwetterService {

    private static final String REGEX_SPLIT_BY_WORDS = "\\s+";
    private static final String REGEX_PARSE_BY_TAG = "%s(\\w)+$";

    private static final char TAG_MENTION = '@';
    private static final char TAG_TOPIC = '#';

    @Inject
    private AccountService accountService;

    @Inject
    private UserService userService;

    @Inject
    private MessageService messageService;

    @Inject
    private TopicService topicService;

    public void createMessage(String text, User author) throws Exception {
        if (text.length() > MAX_TEXT_CHARACTERS) {
            String message = format("Maximum number of text characters(%d) exceeded.", MAX_TEXT_CHARACTERS);
            throw new InputConstraintViolationException(message);
        }

        List<String> words = asList(text.split(REGEX_SPLIT_BY_WORDS));
        Collection<User> mentions = parseMentions(words);
        Collection<Topic> topics = parseTopics(words);

        Message message = Message.builder().text(text).author(author).mentions(mentions).topics(topics).build();

        author.getMessages().add(message);
        mentions.forEach(m -> m.getMentioned().add(message));
        topics.forEach(t -> t.getMessages().add(message));

        messageService.create(message);
    }

    private Collection<User> parseMentions(Collection<String> words) {
        Collection<String> mentions = parse(words, TAG_MENTION);

        return mentions.stream()
                .map(m -> accountService.read(m).getUser())
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    private Collection<Topic> parseTopics(Collection<String> words) {
        Collection<String> topics = parse(words, TAG_TOPIC);

        return topics.stream()
                .map(t -> {
                    Topic topic = topicService.read(t);
                    return isNull(topic) ? Topic.builder().name(t).build() : topic;
                })
                .collect(toSet());
    }

    private Collection<String> parse(Collection<String> words, char parser) {
        return words.stream()
                .filter(w -> w.matches(format(REGEX_PARSE_BY_TAG, parser)))
                .map(m -> m.substring(1))
                .collect(toSet());
    }

    // TODO like and unlike methods

}
