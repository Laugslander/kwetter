package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.exception.InputConstraintViolationException;
import nl.robinlaugs.kwetter.persistence.UserDao;
import nl.robinlaugs.kwetter.persistence.jpa.JpaDao;
import nl.robinlaugs.kwetter.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.TreeSet;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static nl.robinlaugs.kwetter.domain.User.MAX_BIO_CHARACTERS;

/**
 * @author Robin Laugs
 */
@Stateless
public class UserMainService extends BaseMainService<User> implements UserService {

    @Inject
    @JpaDao
    private UserDao dao;

    @PostConstruct
    private void setUp() {
        super.setDao(dao);
    }

    @Override
    public void update(User user) throws Exception {
        if (user.getBio().length() > MAX_BIO_CHARACTERS) {
            String message = format("Maximum number of bio characters (%d) exceeded.", MAX_BIO_CHARACTERS);
            throw new InputConstraintViolationException(message);
        }

        dao.update(user);
    }

    public Collection<Message> readAll(User user) {
        Collection<Message> messages = new TreeSet<>(user.getMessages());
        user.getFollowings().forEach(f -> messages.addAll(f.getMessages()));

        return messages;
    }

    public Collection<Message> readOwn(User user, int limit) {
        return user.getMessages().stream()
                .sorted()
                .limit(limit)
                .collect(toSet());
    }

}
