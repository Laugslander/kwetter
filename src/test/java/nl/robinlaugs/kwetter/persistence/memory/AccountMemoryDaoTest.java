package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.persistence.AccountDao;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Robin Laugs
 */
public class AccountMemoryDaoTest {

    private AccountDao dao;

    @Before
    public void setUp() {
        dao = new AccountMemoryDao();
    }

    @Test
    public void readByUsername_validUsername_returnsAccount() {
        Account account = new Account();
        account.setUsername("username");

        dao.create(account);

        assertThat(dao.readByUsername("username"), is(account));
    }

    @Test
    public void readByUsername_invalidUsername_returnsNull() {
        Account account = new Account();
        account.setUsername("username");

        dao.create(account);

        assertThat(dao.readByUsername("invalid"), is(nullValue()));
    }

    @Test
    public void readByCredentials_validCredentials_returnsAccount() {
        Account account = new Account();
        account.setUsername("username");
        account.setPassword("password");

        dao.create(account);

        assertThat(dao.readByCredentials("username", "password"), is(account));
    }

    @Test
    public void readByCredentials_invalidUsername_returnsNull() {
        Account account = new Account();
        account.setUsername("username");
        account.setPassword("password");

        dao.create(account);

        assertThat(dao.readByCredentials("invalid", "password"), is(nullValue()));
    }

    @Test
    public void readByCredentials_invalidPassword_returnsNull() {
        Account account = new Account();
        account.setUsername("username");
        account.setPassword("password");

        dao.create(account);

        assertThat(dao.readByCredentials("username", "invalid"), is(nullValue()));
    }

}