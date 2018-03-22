package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.persistence.TopicDao;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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
        Topic topic = new Topic();
        topic.setName("name");

        dao.create(topic);

        assertThat(dao.readByName("name"), is(topic));
    }

    @Test
    public void readByName_invalidName_returnsNull() {
        Topic topic = new Topic();
        topic.setName("name");

        dao.create(topic);

        assertThat(dao.readByName("invalid"), is(nullValue()));
    }

}