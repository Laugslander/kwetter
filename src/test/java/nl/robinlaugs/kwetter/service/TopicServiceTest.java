package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.persistence.TopicDao;
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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

    private TopicService service;

    @Mock
    private TopicDao dao;

    @Before
    public void setUp() {
        service = new TopicService(dao);
    }

    @Test
    public void read_validName_callsDao() {
        Topic topic = Topic.builder().name("name").build();

        when(dao.readByName("name")).thenReturn(topic);

        service.read("name");

        verify(dao, times(1)).readByName("name");
    }

    @Test
    public void readTrendingTopics_validFromTimestampAndLimit_returnsTrendingTopics() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Topic topic1 = Topic.builder().build();
        topic1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Topic topic2 = Topic.builder().build();
        topic2.setTimestamp(timestamp2);

        Collection<Topic> topics = asList(topic1, topic2);

        LocalDateTime timestamp = of(2018, JANUARY, 1, 0, 0);
        when(dao.readFromTimestamp(timestamp)).thenReturn(topics);

        Collection<Topic> actual = service.readTrendingTopics(timestamp, 1);
        Collection<Topic> expected = asList(topic2);

        assertThat(actual.size(), is(equalTo(1)));
        assertThat(actual.containsAll(expected), is(true));
    }
}