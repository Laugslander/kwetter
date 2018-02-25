package nl.robinlaugs.kwetter.persistence;

import nl.robinlaugs.kwetter.domain.Topic;

/**
 * @author Robin Laugs
 */
public interface TopicDao extends GenericDao<Topic> {

    Topic readByName(String name);

}
