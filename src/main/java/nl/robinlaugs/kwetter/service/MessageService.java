package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.interceptor.MentionParsingInterceptor;
import nl.robinlaugs.kwetter.service.interceptor.TextParsingInterceptor;
import nl.robinlaugs.kwetter.service.interceptor.TopicParsingInterceptor;

import javax.interceptor.Interceptors;
import java.util.Collection;

/**
 * @author Robin Laugs
 */
public interface MessageService extends GenericService<Message> {

    @Override
    @Interceptors({TextParsingInterceptor.class, MentionParsingInterceptor.class, TopicParsingInterceptor.class})
    void create(Message message) throws Exception;

    /**
     * Searches all existing {@link Message Messages}.
     *
     * @param text The search query which should (partially) match the {@code text} of existing {@link Message Messages}.
     * @return The {@link Message messages} that were found.
     */
    Collection<Message> search(String text);

    /**
     * Likes an existing {@link Message} as a {@link User}.
     *
     * @param message The {@link Message} that should be liked.
     * @param user    The {@link User} that should like the {@link Message}.
     */
    void like(Message message, User user);

    /**
     * Unlikes an existing {@link Message} as a {@link User}.
     *
     * @param message The {@link Message} that should be unliked.
     * @param user    The {@link User} that should unlike the {@link Message}.
     */
    void unlike(Message message, User user);

}
