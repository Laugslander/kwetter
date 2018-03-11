package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.exception.InputConstraintViolationException;
import nl.robinlaugs.kwetter.exception.NullArgumentException;
import nl.robinlaugs.kwetter.exception.UnknownEntityException;
import nl.robinlaugs.kwetter.persistence.MessageDao;
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
import static nl.robinlaugs.kwetter.domain.Message.MAX_TEXT_CHARACTERS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageMainServiceTest {

    @InjectMocks
    private MessageMainService service;

    @Mock
    private MessageDao dao;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void create_validMessage_callsDao() throws Exception {
        Message message = new Message("text");
        message.setAuthor(new User());

        service.create(message);
    }

    @Test(expected = NullArgumentException.class)
    public void create_nullMessageText_throwsException() throws Exception {
        Message message = new Message();
        message.setAuthor(new User());

        service.create(message);
    }

    @Test
    public void create_nullMessageAuthor_throwsException() throws Exception {
        Message message = new Message("text");
        message.setAuthor(new User());

        service.create(message);
    }

    @Test(expected = InputConstraintViolationException.class)
    public void create_tooLongMessageText_throwsException() throws Exception {
        Message message = new Message();
        message.setText(new String(new char[MAX_TEXT_CHARACTERS + 1]).replace("\0", "a"));
        message.setAuthor(new User());

        service.create(message);
    }

    @Test
    public void update_validMessageIdAndUpdate_callsDao() throws Exception {
        Message message = new Message();

        when(dao.read(1L)).thenReturn(message);

        service.update(1L, message);

        verify(dao).update(message);
    }

    @Test(expected = UnknownEntityException.class)
    public void update_unknownMessageId_throwsException() throws Exception {
        Message message = new Message();

        when(dao.read(1L)).thenReturn(null);

        service.update(1L, message);
    }

    @Test(expected = InputConstraintViolationException.class)
    public void update_tooLongMessageText_throwsException() throws Exception {
        Message message = new Message();
        message.setText(new String(new char[MAX_TEXT_CHARACTERS + 1]).replace("\0", "a"));

        when(dao.read(1L)).thenReturn(message);

        service.update(1L, message);
    }

    @Test
    public void search_existingMessageText_returnsCorrespondingMessages() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Message message1 = new Message();
        message1.setText("message1");
        message1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Message message2 = new Message();
        message2.setText("message2");
        message2.setTimestamp(timestamp2);

        LocalDateTime timestamp3 = of(2020, JANUARY, 1, 0, 0);
        Message message3 = new Message();
        message3.setText("message3");
        message3.setTimestamp(timestamp3);

        Collection<Message> messages = asList(message1, message2, message3);

        when(dao.readAll()).thenReturn(messages);

        // search query 1

        Collection<Message> actual1 = service.search("message");

        assertThat(actual1.size(), is(equalTo(3)));
        assertThat(actual1, containsInAnyOrder(message1, message2, message3));

        // search query 1

        Collection<Message> actual2 = service.search("message1");

        assertThat(actual2.size(), is(equalTo(1)));
        assertThat(actual2, containsInAnyOrder(message1));

        // search query 1

        Collection<Message> actual3 = service.search("eSSaGe1");

        assertThat(actual3.size(), is(equalTo(1)));
        assertThat(actual3, containsInAnyOrder(message1));
    }

    @Test
    public void search_nonExistingMessageText_returnsEmptyCollectionOfMessages() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Message message1 = new Message();
        message1.setText("message1");
        message1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Message message2 = new Message();
        message2.setText("message2");
        message2.setTimestamp(timestamp2);

        LocalDateTime timestamp3 = of(2020, JANUARY, 1, 0, 0);
        Message message3 = new Message();
        message3.setText("message3");
        message3.setTimestamp(timestamp3);

        Collection<Message> messages = asList(message1, message2, message3);

        when(dao.readAll()).thenReturn(messages);

        Collection<Message> actual = service.search("non existing message");

        assertThat(actual.size(), is(equalTo(0)));
        assertThat(actual, is(emptyCollectionOf(Message.class)));
    }

    @Test
    public void like_validMessageAndUser_userLikesMessage() {
        Message message = new Message();
        User user = new User();

        service.like(message, user);

        assertThat(message.getLikes(), contains(user));
        assertThat(user.getLiked(), contains(message));
    }

    @Test
    public void unlike_validMessageAndUser_userUnlikedMessage() {
        Message message = new Message();
        User user = new User();

        message.getLikes().add(user);
        user.getLiked().add(message);

        service.unlike(message, user);

        assertThat(message.getLikes(), not(contains(user)));
        assertThat(user.getLiked(), not(contains(message)));
    }

}