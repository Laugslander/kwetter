package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.persistence.MessageDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.TreeSet;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    private MessageService service;

    @Mock
    private MessageDao dao;

    @Before
    public void setUp() {
        service = new MessageService(dao);
    }

    @Test
    public void search_existingMessageText_returnsCorrespondingMessages() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Message message1 = Message.builder().text("message 1").build();
        message1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Message message2 = Message.builder().text("message 2").build();
        message1.setTimestamp(timestamp2);

        LocalDateTime timestamp3 = of(2020, JANUARY, 1, 0, 0);
        Message message3 = Message.builder().text("message 3").build();
        message1.setTimestamp(timestamp3);

        Collection<Message> messages = asList(message1, message2, message3);

        when(dao.readAll()).thenReturn(messages);

        // search query 1

        Collection<Message> actual1 = service.search("message");
        Collection<Message> expected1 = asList(message1, message2, message3);

        assertThat(actual1.size(), is(equalTo(3)));
        assertThat(actual1.containsAll(expected1), is(true));

        // search query 1

        Collection<Message> actual2 = service.search("message 1");
        Collection<Message> expected2 = asList(message1);

        assertThat(actual2.size(), is(equalTo(1)));
        assertThat(actual2.containsAll(expected2), is(true));

        // search query 1

        Collection<Message> actual3 = service.search("eSSaGe 1");
        Collection<Message> expected3 = asList(message1);

        assertThat(actual3.size(), is(equalTo(1)));
        assertThat(actual3.containsAll(expected3), is(true));
    }

    @Test
    public void search_nonExistingMessageText_returnsEmptyCollectionOfMessages() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Message message1 = Message.builder().text("message 1").build();
        message1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Message message2 = Message.builder().text("message 2").build();
        message1.setTimestamp(timestamp2);

        LocalDateTime timestamp3 = of(2020, JANUARY, 1, 0, 0);
        Message message3 = Message.builder().text("message 3").build();
        message1.setTimestamp(timestamp3);

        Collection<Message> messages = asList(message1, message2, message3);

        when(dao.readAll()).thenReturn(messages);

        Collection<Message> actual = service.search("non existing message");
        Collection<Message> expected = new TreeSet<>();

        assertThat(actual.size(), is(equalTo(0)));
        assertThat(actual.containsAll(expected), is(true));
    }

}