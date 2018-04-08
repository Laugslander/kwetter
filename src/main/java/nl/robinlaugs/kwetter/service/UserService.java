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

    /**
     * Follows an existing {@link User} as another @{link User}.
     *
     * @param user     The {@link User} that should be followed.
     * @param follower The {@link User} that want follow the user.
     */
    void follow(User user, User follower);

    /**
     * Unfollows an existing {@link User} as another {@link User}.
     *
     * @param user     The {@link User} that should be unfollowed.
     * @param follower The {@link User} that wants to unfollow the user.
     */
    void unfollow(User user, User follower);

    /**
     * Searches all existing {@link User Users}.
     *
     * @param text The search query which should (partially) match the existing {@link User Users}.
     * @return The {@link User Users} that were found.
     */
    Collection<User> search(String text);

}
