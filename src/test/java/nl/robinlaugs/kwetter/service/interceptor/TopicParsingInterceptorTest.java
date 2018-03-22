package nl.robinlaugs.kwetter.service.interceptor;

import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.service.main.TopicMainService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class TopicParsingInterceptorTest {

    @InjectMocks
    private TopicParsingInterceptor interceptor;

    @Mock
    private TopicMainService service;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void parseTopics_validWords_returnsTopics() {
        Collection<String> topics = asList("#topic1", "topic2", "topic2");
        Topic topic = new Topic();
        topic.setName("topic1");

        when(service.read("topic1")).thenReturn(topic);

        Collection<Topic> actual = interceptor.parseTopics(topics);

        assertThat(actual, contains(topic));
    }

}