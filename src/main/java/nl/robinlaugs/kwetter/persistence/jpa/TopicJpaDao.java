package nl.robinlaugs.kwetter.persistence.jpa;

import nl.robinlaugs.kwetter.domain.Topic;
import nl.robinlaugs.kwetter.persistence.TopicDao;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

/**
 * @author Robin Laugs
 */
@Stateless
@Default
@JpaDao
public class TopicJpaDao extends BaseJpaDao<Topic> implements TopicDao {

    @Override
    public Topic readByName(String name) {
        return getManager()
                .createNamedQuery("Topic.getByName", Topic.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public TopicJpaDao() {
        setImplementation(Topic.class);
    }

}
