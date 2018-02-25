package nl.robinlaugs.kwetter.domain;

import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Robin Laugs
 */
public class MessageTest {

    @Test
    public void setText_validText_assignsText() {
        Message message = Message.builder().text("text").build();

        String actual = message.getText();
        String expected = "text";

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setAuthor_validAuthor_assignsAuthor() {
        User author = User.builder().build();
        Message message = Message.builder().author(author).build();

        User actual = message.getAuthor();
        User expected = author;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addLikes_validLikes_assignsLikes() {
        User like = User.builder().build();
        Collection<User> likes = asList(like);
        Message message = Message.builder().likes(likes).build();

        Collection<User> actual = message.getLikes();
        Collection<User> expected = likes;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addLike_validLike_addsLike() {
        User like = User.builder().build();
        Message message = Message.builder().like(like).build();

        assertThat(message.getLikes().contains(like), is(true));
    }

    @Test
    public void addMentions_validMentions_assignsMentions() {
        User mention = User.builder().build();
        Collection<User> mentions = asList(mention);
        Message message = Message.builder().mentions(mentions).build();

        Collection<User> actual = message.getMentions();
        Collection<User> expected = mentions;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addMention_validMention_assignsMention() {
        User mention = User.builder().build();
        Message message = Message.builder().mention(mention).build();

        assertThat(message.getMentions().contains(mention), is(true));
    }

    @Test
    public void addTopics_validTops_assignsTopics() {
        Topic topic = Topic.builder().build();
        Collection<Topic> topics = asList(topic);
        Message message = Message.builder().topics(topics).build();

        Collection<Topic> actual = message.getTopics();
        Collection<Topic> expected = topics;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addTopic_validTopic_assignsTopic() {
        Topic topic = Topic.builder().build();
        Message message = Message.builder().topic(topic).build();

        assertThat(message.getTopics().contains(topic), is(true));
    }

}