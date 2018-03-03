package nl.robinlaugs.kwetter.persistence.jpa;

import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.persistence.UserDao;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

/**
 * @author Robin Laugs
 */
@Stateless
@Default
@JpaDao
public class UserJpaDao extends BaseJpaDao<User> implements UserDao {

    @PostConstruct
    private void setUp() {
        setImplementation(User.class);
    }

}
