package nl.robinlaugs.kwetter.persistence.jpa;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.persistence.AccountDao;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

/**
 * @author Robin Laugs
 */
@Stateless
@Default
@JpaDao
public class AccountJpaDao extends BaseJpaDao<Account> implements AccountDao {

    @PostConstruct
    private void setUp() {
        setImplementation(Account.class);
    }

    @Override
    public Account readByUsername(String username) {
        return getManager()
                .createNamedQuery("Account.getByUsername", Account.class)
                .setParameter("username", username)
                .getResultList().stream()
                .findFirst().orElse(null);
    }

    @Override
    public Account readByCredentials(String username, String password) {
        return getManager()
                .createNamedQuery("Account.getByCredentials", Account.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList().stream()
                .findFirst().orElse(null);
    }

}
