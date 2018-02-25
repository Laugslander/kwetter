package nl.robinlaugs.kwetter.domain;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collection;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Robin Laugs
 */
public class TopicTest {

    @Test
    public void setName_validName_assignsName() {
        Topic topic = Topic.builder().name("name").build();

        String actual = topic.getName();
        String expected = "name";

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addMessages_validMessage_assignsMessages() {
        Message message = Message.builder().build();
        Collection<Message> messages = asList(message);
        Topic topic = Topic.builder().messages(messages).build();

        Collection<Message> actual = topic.getMessages();
        Collection<Message> expected = messages;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addMessage_validMessage_assignsMessage() {
        Message message = Message.builder().build();
        Topic topic = Topic.builder().message(message).build();

        assertThat(topic.getMessages().contains(message), is(true));
    }

    @Test
    public void compareTo_validTopicWithDifferentMessageSize_comparesMessageSize() {
        Message message1 = Message.builder().build();
        Message message2 = Message.builder().build();

        Collection<Message> messages1 = asList(message1);
        Collection<Message> messages2 = asList(message1, message2);

        Topic topic1 = Topic.builder().messages(messages1).build();
        Topic topic2 = Topic.builder().messages(messages2).build();

        assertThat(topic1.compareTo(topic1), is(0));
        assertThat(topic1.compareTo(topic2), is(-1));
        assertThat(topic2.compareTo(topic1), is(1));
    }

    @Test
    public void compareTo_validTopicWithEqualMessageSize_comparesTimestamps() {
        Message message = Message.builder().build();
        Collection<Message> messages = asList(message);

        Topic topic1 = Topic.builder().messages(messages).build();
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        topic1.setTimestamp(timestamp1);

        Topic topic2 = Topic.builder().messages(messages).build();
        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        topic2.setTimestamp(timestamp2);

        assertThat(topic1.compareTo(topic1), is(0));
        assertThat(topic1.compareTo(topic2), is(1));
        assertThat(topic2.compareTo(topic1), is(-1));
    }

}