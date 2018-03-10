package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.TopicDto;
import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.service.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class TopicResourceTest {

    @InjectMocks
    private TopicResource resource;

    @Mock
    private TopicService service;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getTopics_getsTopics() {
        Collection<Topic> topics = new ArrayList<>();
        topics.add(new Topic());
        topics.add(new Topic());

        when(service.readAll()).thenReturn(topics);

        Response response = resource.getTopics();

        Collection<TopicDto> dto = ((Collection<TopicDto>) response.getEntity());

        assertThat(response.getStatus(), is(200));
        assertThat(dto.size(), is(2));
    }

    @Test
    public void getMessage_validMessageId_getsMessage() {
        Topic topic = new Topic("name");
        topic.setId(1L);

        when(service.read(anyLong())).thenReturn(topic);

        Response response = resource.getTopic(1L);

        TopicDto dto = (TopicDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getName(), is("name"));
    }

    @Test
    public void trending_getsTrendingTopics() {
        Collection<Topic> topics = new ArrayList<>();
        topics.add(new Topic());
        topics.add(new Topic());

        when(service.readTrendingTopics(any(LocalDateTime.class), anyInt())).thenReturn(topics);

        Response response = resource.trending();

        Collection<TopicDto> dto = (Collection<TopicDto>) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.size(), is(2));
    }


}