package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.persistence.UserDao;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

/**
 * @author Robin Laugs
 */
@Stateless
@Alternative
@MemoryDao
public class UserMemoryDao extends BaseMemoryDao<User> implements UserDao {
}
