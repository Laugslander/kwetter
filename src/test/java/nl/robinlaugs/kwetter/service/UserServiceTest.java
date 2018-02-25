package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.exception.InputConstraintViolationException;
import nl.robinlaugs.kwetter.persistence.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collection;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static java.util.Arrays.asList;
import static nl.robinlaugs.kwetter.domain.User.MAX_BIO_CHARACTERS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService service;

    @Mock
    private UserDao dao;

    @Before
    public void setUp() {
        service = new UserService(dao);
    }

    @Test(expected = InputConstraintViolationException.class)
    public void update_validBio_callsDao() throws Exception {
        String bio = new String(new char[MAX_BIO_CHARACTERS + 1]).replace("\0", "a");
        User user = User.builder().bio(bio).build();

        service.update(user);
    }

    @Test
    public void readAll_validUser_readsAllMessages() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Message message1 = Message.builder().build();
        message1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Message message2 = Message.builder().build();
        message1.setTimestamp(timestamp2);

        User following = User.builder().message(message1).build();
        User user = User.builder().message(message2).following(following).build();

        Collection<Message> actual = service.readAll(user);
        Collection<Message> expected = asList(message1, message2);

        assertThat(actual.size(), is(equalTo(2)));
        assertThat(actual.containsAll(expected), is(true));
    }

    @Test
    public void readOwn_validUserAndLimit_readsOwnMessages() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Message message1 = Message.builder().build();
        message1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Message message2 = Message.builder().build();
        message2.setTimestamp(timestamp2);

        Collection<Message> messages = asList(message1, message2);

        User user = User.builder().messages(messages).build();

        Collection<Message> actual = service.readOwn(user, 1);
        Collection<Message> expected = asList(message2);

        assertThat(actual.size(), is(equalTo(1)));
        assertThat(actual.containsAll(expected), is(true));
    }

}