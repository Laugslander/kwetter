package nl.robinlaugs.kwetter.persistence;

import nl.robinlaugs.kwetter.domain.Account;

/**
 * @author Robin Laugs
 */
public interface AccountDao extends GenericDao<Account> {

    Account readByUsername(String username);

    Account readByCredentials(String username, String password);

}
