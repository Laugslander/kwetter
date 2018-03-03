package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.exception.InputConstraintViolationException;
import nl.robinlaugs.kwetter.persistence.MessageDao;
import nl.robinlaugs.kwetter.persistence.jpa.JpaDao;
import nl.robinlaugs.kwetter.service.MessageService;
import nl.robinlaugs.kwetter.service.interceptor.MentionParsingInterceptor;
import nl.robinlaugs.kwetter.service.interceptor.TextParsingInterceptor;
import nl.robinlaugs.kwetter.service.interceptor.TopicParsingInterceptor;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Collection;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static nl.robinlaugs.kwetter.domain.Message.MAX_TEXT_CHARACTERS;

/**
 * @author Robin Laugs
 */
@Stateless
public class MessageMainService extends BaseMainService<Message> implements MessageService {

    @Inject
    @JpaDao
    private MessageDao dao;

    @PostConstruct
    private void setUp() {
        super.setDao(dao);
    }

    @Override
    @Interceptors({TextParsingInterceptor.class, MentionParsingInterceptor.class, TopicParsingInterceptor.class})
    public void create(Message message) throws Exception {
        String text = message.getText();

        if (text.length() > MAX_TEXT_CHARACTERS) {
            String exception = format("Maximum number of text characters(%d) exceeded.", MAX_TEXT_CHARACTERS);
            throw new InputConstraintViolationException(exception);
        }

        message.getAuthor().getMessages().add(message);
        message.getMentions().forEach(m -> m.getMentioned().add(message));
        message.getTopics().forEach(t -> t.getMessages().add(message));

        dao.create(message);
    }

    public Collection<Message> search(String text) {
        return dao.readAll().stream()
                .filter(m -> m.getText().toLowerCase().contains(text.toLowerCase()))
                .sorted()
                .collect(toSet());
    }

    public void like(Message message, User user) {
        message.getLikes().add(user);
        user.getLiked().add(message);

        dao.update(message);
    }

    public void unlike(Message message, User user) {
        message.getLikes().remove(user);
        user.getLiked().remove(message);

        dao.update(message);
    }

}
