package nl.robinlaugs.kwetter.persistence.jpa;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.persistence.MessageDao;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

/**
 * @author Robin Laugs
 */
@Stateless
@Default
@JpaDao
public class MessageJpaDao extends BaseJpaDao<Message> implements MessageDao {

    public MessageJpaDao() {
        setImplementation(Message.class);
    }

}
