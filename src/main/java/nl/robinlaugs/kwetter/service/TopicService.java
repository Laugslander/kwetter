package nl.robinlaugs.kwetter.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.persistence.TopicDao;
import nl.robinlaugs.kwetter.persistence.jpa.JpaDao;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;

/**
 * @author Robin Laugs
 */
@Stateless
@AllArgsConstructor(onConstructor = @__(@Inject))
@NoArgsConstructor
public class TopicService extends BaseService<Topic> {

    @JpaDao
    private TopicDao dao;

    @PostConstruct
    private void setUp() {
        super.setDao(dao);
    }

    public Topic read(String name) {
        return dao.readByName(name);
    }

    public Collection<Topic> readTrendingTopics(LocalDateTime from, int limit) {
        Collection<Topic> topics = dao.readFromTimestamp(from);

        return topics.stream()
                .sorted()
                .limit(limit)
                .collect(toSet());
    }

}
