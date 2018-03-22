package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.persistence.TopicDao;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

/**
 * @author Robin Laugs
 */
@Stateless
@Alternative
@MemoryDao
public class TopicMemoryDao extends BaseMemoryDao<Topic> implements TopicDao {

    @Override
    public Topic readByName(String name) {
        return getEntities().values().stream()
                .filter(t -> t.getName().equals(name))
                .findFirst().orElse(null);
    }

}
