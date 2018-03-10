package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.exception.DuplicateUsernameException;
import nl.robinlaugs.kwetter.exception.InvalidCredentialsException;
import nl.robinlaugs.kwetter.persistence.AccountDao;
import nl.robinlaugs.kwetter.persistence.jpa.JpaDao;
import nl.robinlaugs.kwetter.service.AccountService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author Robin Laugs
 */
@Stateless
public class AccountMainService extends BaseMainService<Account> implements AccountService {

    @Inject
    @JpaDao
    private AccountDao dao;

    @PostConstruct
    private void setUp() {
        super.setDao(dao);
    }

    @Override
    public void create(Account account) throws Exception {
        if (nonNull(dao.readByUsername(account.getUsername()))) throw new DuplicateUsernameException();

        account.getUser().setAccount(account);

        super.create(account);
    }

    @Override
    public void update(Account account) throws Exception {
        if (nonNull(dao.readByUsername(account.getUsername()))) throw new DuplicateUsernameException();

        super.update(account);
    }

    @Override
    public Account read(String username) {
        return dao.readByUsername(username);
    }

    @Override
    public Account read(String username, String password) throws Exception {
        Account account = dao.readByCredentials(username, password);

        if (isNull(account)) throw new InvalidCredentialsException();

        return account;
    }

}
