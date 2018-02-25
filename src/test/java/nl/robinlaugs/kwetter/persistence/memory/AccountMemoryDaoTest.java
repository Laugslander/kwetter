package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.persistence.AccountDao;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
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
        Account account = Account.builder().username("username").build();
        dao.create(account);

        Account actual = dao.readByUsername("username");
        Account expected = account;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void readByUsername_invalidUsername_returnsNull() {
        Account account = Account.builder().username("username").build();
        dao.create(account);

        Account actual = dao.readByUsername("invalid");

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void readByCredentials_validCredentials_returnsAccount() {
        Account account = Account.builder().username("username").password("password").build();
        dao.create(account);

        Account actual = dao.readByCredentials("username", "password");
        Account expected = account;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void readByCredentials_invalidUsername_returnsNull() {
        Account account = Account.builder().username("username").password("password").build();
        dao.create(account);

        Account actual = dao.readByCredentials("invalid", "password");

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void readByCredentials_invalidPassword_returnsNull() {
        Account account = Account.builder().username("username").password("password").build();
        dao.create(account);

        Account actual = dao.readByCredentials("username", "invalid");

        assertThat(actual, is(nullValue()));
    }

}