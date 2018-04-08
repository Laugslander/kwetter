package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.exception.NullArgumentException;
import nl.robinlaugs.kwetter.exception.UnknownEntityException;
import nl.robinlaugs.kwetter.persistence.TopicDao;
import nl.robinlaugs.kwetter.persistence.jpa.JpaDao;
import nl.robinlaugs.kwetter.service.TopicService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * @author Robin Laugs
 */
@Stateless
public class TopicMainService extends BaseMainService<Topic> implements TopicService {

    @Inject
    @JpaDao
    private TopicDao dao;

    @PostConstruct
    private void setUp() {
        super.setDao(dao);
    }

    @Override
    public void create(Topic topic) throws Exception {
        String name = topic.getName();

        if (isNull(name) || name.isEmpty()) throw new NullArgumentException("Name cannot be empty");

        dao.create(topic);
    }

    @Override
    public Topic update(Long id, Topic update) throws Exception {
        Topic topic = dao.read(id);

        if (isNull(topic)) throw new UnknownEntityException(format("Topic with id %d does not exist", id));

        String name = topic.getName();

        if (nonNull(name)) topic.setName(name);

        return dao.update(topic);
    }

    @Override
    public Topic read(Long id) throws Exception {
        Topic topic = dao.read(id);

        if (isNull(topic)) throw new UnknownEntityException(format("Topic with id %d does not exist", id));

        return topic;
    }

    @Override
    public Topic read(String name) {
        return dao.readByName(name);
    }

    @Override
    public Collection<Topic> readTrendingTopics(LocalDateTime from, int limit) {
        Collection<Topic> topics = dao.readFromTimestamp(from);

        return topics.stream()
                .sorted()
                .limit(limit)
                .collect(toList());
    }

}
