package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.persistence.AccountDao;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

/**
 * @author Robin Laugs
 */
@Stateless
@Alternative
@MemoryDao
public class AccountMemoryDao extends BaseMemoryDao<Account> implements AccountDao {

    @Override
    public Account readByUsername(String username) {
        return getEntities().values().stream()
                .filter(a -> a.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @Override
    public Account readByCredentials(String username, String password) {
        return getEntities().values().stream()
                .filter(a -> a.getUsername().equals(username) && a.getPassword().equals(password))
                .findFirst().orElse(null);
    }

}

