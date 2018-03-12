package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.exception.InputConstraintViolationException;
import nl.robinlaugs.kwetter.exception.UnknownEntityException;
import nl.robinlaugs.kwetter.persistence.UserDao;
import nl.robinlaugs.kwetter.persistence.jpa.JpaDao;
import nl.robinlaugs.kwetter.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.TreeSet;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
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
    public void create(User user) throws Exception {
        checkMaxBioCharacters(user.getBio());

        dao.create(user);
    }

    @Override
    public User update(Long id, User update) throws Exception {
        User user = dao.read(id);

        if (isNull(user)) throw new UnknownEntityException(format("User with id %d does not exist", id));

        String name = update.getName();
        String avatar = update.getAvatar();
        String location = update.getLocation();
        String website = update.getWebsite();
        String bio = update.getBio();

        checkMaxBioCharacters(bio);

        if (nonNull(name)) user.setName(name);
        if (nonNull(avatar)) user.setAvatar(avatar);
        if (nonNull(location)) user.setLocation(location);
        if (nonNull(website)) user.setWebsite(website);
        if (nonNull(bio)) user.setBio(bio);

        return dao.update(user);
    }

    @Override
    public User read(Long id) throws Exception {
        User user = dao.read(id);

        if (isNull(user)) throw new UnknownEntityException(format("User with id %d does not exist", id));

        return user;
    }

    @Override
    public Collection<Message> readAll(User user) {
        Collection<Message> messages = new TreeSet<>(user.getMessages());
        user.getFollowings().forEach(f -> messages.addAll(f.getMessages()));

        return messages;
    }

    @Override
    public Collection<Message> readOwn(User user, int limit) {
        return user.getMessages().stream()
                .sorted()
                .limit(limit)
                .collect(toSet());
    }

    @Override
    public void follow(User user, User follower) {
        user.getFollowers().add(follower);
        follower.getFollowings().add(user);
    }

    @Override
    public void unfollow(User user, User follower) {
        user.getFollowers().remove(follower);
        follower.getFollowings().remove(user);
    }

    private void checkMaxBioCharacters(String bio) throws InputConstraintViolationException {
        if (nonNull(bio) && bio.length() > MAX_BIO_CHARACTERS) {
            String message = format("Maximum number of bio characters (%d) exceeded.", MAX_BIO_CHARACTERS);
            throw new InputConstraintViolationException(message);
        }
    }

}
