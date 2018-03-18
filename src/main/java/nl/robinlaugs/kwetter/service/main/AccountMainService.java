package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Role;
import nl.robinlaugs.kwetter.exception.DuplicateUsernameException;
import nl.robinlaugs.kwetter.exception.InvalidCredentialsException;
import nl.robinlaugs.kwetter.exception.NullArgumentException;
import nl.robinlaugs.kwetter.exception.UnknownEntityException;
import nl.robinlaugs.kwetter.persistence.AccountDao;
import nl.robinlaugs.kwetter.persistence.jpa.JpaDao;
import nl.robinlaugs.kwetter.service.AccountService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

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
        String username = account.getUsername();
        String password = account.getPassword();

        if (isNull(username)) throw new NullArgumentException("Username cannot be null");
        if (isNull(password)) throw new NullArgumentException("Password cannot be null");

        if (nonNull(dao.readByUsername(username))) throw new DuplicateUsernameException();

        account.getUser().setAccount(account);

        dao.create(account);
    }

    @Override
    public Account update(Long id, Account update) throws Exception {
        Account account = dao.read(id);

        if (isNull(account)) throw new UnknownEntityException(format("Account with id %d does not exist", id));

        String username = update.getUsername();
        String password = update.getPassword();
        Role role = update.getRole();

        if (nonNull(username) && nonNull(dao.readByUsername(username))) throw new DuplicateUsernameException();

        if (nonNull(username)) account.setUsername(username);
        if (nonNull(password)) account.setPassword(password);
        if (nonNull(role)) account.setRole(role);

        return dao.update(account);
    }

    @Override
    public Account read(Long id) throws Exception {
        Account account = dao.read(id);

        if (isNull(account)) throw new UnknownEntityException(format("Account with id %d does not exist", id));

        return account;
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

    @Override
    public Collection<Account> search(String text) {
        return dao.readAll().stream()
                .filter(a -> contains(a.getUsername(), text) ||
                        contains(a.getRole().toString(), text) ||
                        contains(a.getUser().getName(), text))
                .sorted()
                .collect(toList());
    }

    private boolean contains(String text, String matcher) {
        return text.toLowerCase().contains(matcher.toLowerCase());
    }

}
