package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.persistence.TopicDao;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Robin Laugs
 */
public class TopicMemoryDaoTest {

    private TopicDao dao;

    @Before
    public void setUp() {
        dao = new TopicMemoryDao();
    }

    @Test
    public void readByName_validName_returnsTopic() {
        Topic topic = Topic.builder().name("name").build();
        dao.create(topic);

        Topic actual = dao.readByName("name");
        Topic expected = topic;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void readByName_invalidName_returnsNull() {
        Topic topic = Topic.builder().name("name").build();
        dao.create(topic);

        Topic actual = dao.readByName("invalid");

        assertThat(actual, is(nullValue()));
    }

}