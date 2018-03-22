package nl.robinlaugs.kwetter.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Robin Laugs
 */
public class MessageTest {

    @Test
    public void initialize_initializesLikes() {
        Message message = new Message();

        assertThat(message.getLikes(), is(notNullValue()));
    }

    @Test
    public void initialize_initializesMentions() {
        Message message = new Message();

        assertThat(message.getMentions(), is(notNullValue()));
    }

    @Test
    public void initialize_initializesTopics() {
        Message message = new Message();

        assertThat(message.getTopics(), is(notNullValue()));
    }

    @Test
    public void setText_validText_assignsText() {
        Message message = new Message();
        message.setText("text");

        assertThat(message.getText(), is("text"));
    }

    @Test
    public void setAuthor_validAuthor_assignsAuthor() {
        User author = new User();

        Message message = new Message();
        message.setAuthor(author);

        assertThat(message.getAuthor(), is(author));
    }

}