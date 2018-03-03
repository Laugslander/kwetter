package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.persistence.TopicDao;
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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class TopicMainServiceTest {

    @InjectMocks
    private TopicMainService service;

    @Mock
    private TopicDao dao;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void read_validName_callsDao() {
        Topic topic = new Topic();
        topic.setName("name");

        when(dao.readByName("name")).thenReturn(topic);

        service.read("name");

        verify(dao, times(1)).readByName("name");
    }

    @Test
    public void readTrendingTopics_validFromTimestampAndLimit_returnsTrendingTopics() {
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        Topic topic1 = new Topic();
        topic1.setTimestamp(timestamp1);

        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        Topic topic2 = new Topic();
        topic2.setTimestamp(timestamp2);

        Collection<Topic> topics = asList(topic1, topic2);

        LocalDateTime timestamp = of(2018, JANUARY, 1, 0, 0);
        when(dao.readFromTimestamp(timestamp)).thenReturn(topics);

        Collection<Topic> actual = service.readTrendingTopics(timestamp, 1);

        assertThat(actual.size(), is(equalTo(1)));
        assertThat(actual, contains(topic2));
    }
}