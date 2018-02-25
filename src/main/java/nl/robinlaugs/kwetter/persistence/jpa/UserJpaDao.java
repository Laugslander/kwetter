package nl.robinlaugs.kwetter.persistence.jpa;

import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.persistence.UserDao;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

/**
 * @author Robin Laugs
 */
@Stateless
@Default
@JpaDao
public class UserJpaDao extends BaseJpaDao<User> implements UserDao {

    public UserJpaDao() {
        setImplementation(User.class);
    }

}
