package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;

import java.util.Collection;

/**
 * @author Robin Laugs
 */
public interface UserService extends GenericService<User> {

    /**
     * Reads all existing {@link Message Messages}.
     *
     * @param user The {@link User} whose existing messages should be read.
     * @return The {@link Message Messages} that were read.
     */
    Collection<Message> readAll(User user);

    /**
     * Reads all own existing {@link Message Messages}.
     *
     * @param user  The {@link User} whose own existing messages should be read.
     * @param limit The maximum number of {@link Message Messages} that should be read.
     * @return The {@link Message Messages} that were read.
     */
    Collection<Message> readOwn(User user, int limit);

}
