package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.exception.InputConstraintViolationException;
import nl.robinlaugs.kwetter.exception.UnknownEntityException;
import nl.robinlaugs.kwetter.persistence.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collection;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static java.util.Arrays.asList;
import static nl.robinlaugs.kwetter.domain.User.MAX_BIO_CHARACTERS;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class UserMainServiceTest {

    @InjectMocks
    private UserMainService service;

    @Mock
    private UserDao dao;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void create_validAccount_callsDao() throws Exception {
        User user = new User();

        service.create(user);

        verify(dao).create(user);
    }

    @Test(expected = InputConstraintViolationException.class)
    public void create_tooLongUserBio_throwsException() throws Exception {
        User user = new User();
        user.setBio(new String(new char[MAX_BIO_CHARACTERS + 1]).replace("\0", "a"));

        service.create(user);
    }

    @Test
    public void update_validUserIdAndUpdate_callsDao() throws Exception {
        User user = new User();

        when(dao.read(1L)).thenReturn(user);

        service.update(1L, user);

        verify(dao).update(user);
    }

    @Test(expected = UnknownEntityException.class)
    public void update_unknownUserId_throwsException() throws Exception {
        User user = new User();

        when(dao.read(1L)).thenReturn(null);

        service.update(1L, user);
    }

    @Test(expected = InputConstraintViolationException.class)
    public void update_tooLongUserBio_throwsException() throws Exception {
        User user = new User();
        user.setBio(new String(new char[MAX_BIO_CHARACTERS + 1]).replace("\0", "a"));

        when(dao.read(1L)).thenReturn(user);

        service.update(1L, user);
    }

    @Test
    public void readAll_validUser_readsAllMessages() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Message message1 = new Message();
        message1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Message message2 = new Message();
        message1.setTimestamp(timestamp2);

        User following = new User();
        following.getMessages().add(message1);

        User user = new User();
        user.getMessages().add(message2);
        user.getFollowings().add(following);

        Collection<Message> actual = service.readAll(user);

        assertThat(actual.size(), is(equalTo(2)));
        assertThat(actual, containsInAnyOrder(message1, message2));
    }

    @Test
    public void readOwn_validUserAndLimit_readsOwnMessages() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Message message1 = new Message();
        message1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Message message2 = new Message();
        message2.setTimestamp(timestamp2);

        Collection<Message> messages = asList(message1, message2);

        User user = new User();
        user.setMessages(messages);

        Collection<Message> actual = service.readOwn(user, 1);

        assertThat(actual.size(), is(equalTo(1)));
        assertThat(actual, contains(message2));
    }

    @Test
    public void follow_validUserAndFollower_userFollowedByFollower() {
        User user = new User();
        User follower = new User();

        service.follow(user, follower);

        assertThat(user.getFollowers(), contains(follower));
        assertThat(follower.getFollowings(), contains(user));
    }

    @Test
    public void unfollow_validUserAndFollower_userUnfollowedByFollower() {
        User user = new User();
        User follower = new User();

        user.getFollowers().add(follower);
        follower.getFollowings().add(user);

        service.unfollow(user, follower);

        assertThat(user.getFollowers(), not(contains(follower)));
        assertThat(follower.getFollowings(), not(contains(user)));
    }

}