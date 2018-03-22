package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.persistence.MessageDao;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

/**
 * @author Robin Laugs
 */
@Stateless
@Alternative
@MemoryDao
public class MessageMemoryDao extends BaseMemoryDao<Message> implements MessageDao {
}
