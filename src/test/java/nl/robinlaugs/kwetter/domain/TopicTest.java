package nl.robinlaugs.kwetter.domain;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Robin Laugs
 */
public class TopicTest {

    @Test
    public void initialize_initializesMessages() {
        Topic topic = new Topic();

        assertThat(topic.getMessages(), is(notNullValue()));
    }

    @Test
    public void setName_validName_assignsName() {
        Topic topic = new Topic();
        topic.setName("name");

        assertThat(topic.getName(), is("name"));
    }

    @Test
    @Ignore
    public void compareTo_validTopicWithDifferentMessageSize_comparesMessageSize() {
        Message message1 = new Message();
        Message message2 = new Message();

        Collection<Message> messages1 = asList(message1);
        Collection<Message> messages2 = asList(message1, message2);

        Topic topic1 = new Topic();
        topic1.setMessages(messages1);
        Topic topic2 = new Topic();
        topic2.setMessages(messages2);

        assertThat(topic1.compareTo(topic1), is(0));
        assertThat(topic1.compareTo(topic2), is(-1));
        assertThat(topic2.compareTo(topic1), is(1));
    }

    @Test
    public void compareTo_validTopicWithEqualMessageSize_comparesTimestamps() {
        Message message = new Message();
        Collection<Message> messages = asList(message);

        Topic topic1 = new Topic();
        topic1.setMessages(messages);
        topic1.setTimestamp(of(2018, JANUARY, 1, 0, 0));

        Topic topic2 = new Topic();
        topic2.setMessages(messages);
        topic2.setTimestamp(of(2019, JANUARY, 1, 0, 0));

        assertThat(topic1.compareTo(topic1), is(0));
        assertThat(topic1.compareTo(topic2), is(1));
        assertThat(topic2.compareTo(topic1), is(-1));
    }

}