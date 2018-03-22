package nl.robinlaugs.kwetter.service.interceptor;

import lombok.extern.java.Log;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.service.TopicService;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Collection;

import static java.util.Objects.isNull;
import static java.util.logging.Level.WARNING;
import static java.util.stream.Collectors.toSet;

/**
 * @author Robin Laugs
 */
@Interceptor
@ParsingInterceptor
@Log
public class TopicParsingInterceptor extends BaseParsingInterceptor {

    private static final char TAG_TOPIC = '#';

    @Inject
    private TopicService service;

    @AroundInvoke
    public Object parseMessage(InvocationContext context) {
        Object[] parameters = context.getParameters();
        Message message = (Message) parameters[0];

        Collection<String> words = splitByWords(message.getText());
        Collection<Topic> topic = parseTopics(words);

        message.setTopics(topic);

        parameters[0] = message;
        context.setParameters(parameters);

        try {
            return context.proceed();
        } catch (Exception e) {
            log.log(WARNING, "An error occurred while parsing the message for topics", e);
            return null;
        }
    }

    Collection<Topic> parseTopics(Collection<String> words) {
        Collection<String> topics = parse(words, TAG_TOPIC);

        return topics.stream()
                .map(this::createTopic)
                .collect(toSet());
    }

    private Topic createTopic(String name) {
        Topic topic = service.read(name);

        if (isNull(topic)) {
            topic = new Topic();
            topic.setName(name);

            try {
                service.create(topic);
            } catch (Exception e) {
                log.log(WARNING, "An error occurred while creating a topic", e);
            }

            return topic;
        }

        return topic;
    }

}
