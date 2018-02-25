package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.exception.DuplicateUsernameException;
import nl.robinlaugs.kwetter.exception.InvalidCredentialsException;
import nl.robinlaugs.kwetter.persistence.AccountDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    private AccountService service;

    @Mock
    private AccountDao dao;

    @Before
    public void setUp() {
        service = new AccountService(dao);
    }

    @Test(expected = DuplicateUsernameException.class)
    public void create_accountWithDuplicateUsername_throwsException() throws Exception {
        Account account = Account.builder().username("username").build();

        when(dao.readByUsername("username")).thenReturn(account);

        service.create(account);
    }

    @Test(expected = DuplicateUsernameException.class)
    public void update_accountWithDuplicateUsername_throwsException() throws Exception {
        Account account = Account.builder().username("username").build();

        when(dao.readByUsername("username")).thenReturn(account);

        service.update(account);
    }

    @Test
    public void read_validUsername_callsDao() {
        Account account = Account.builder().username("username").build();

        when(dao.readByUsername("username")).thenReturn(account);

        service.read("username");

        verify(dao, times(1)).readByUsername("username");
    }

    @Test
    public void read_validCredentials_callsDao() throws Exception {
        Account account = Account.builder().username("username").password("password").build();

        when(dao.readByCredentials("username", "password")).thenReturn(account);

        service.read("username", "password");

        verify(dao, times(1)).readByCredentials("username", "password");
    }

    @Test(expected = InvalidCredentialsException.class)
    public void read_invalidUsername_throwsException() throws Exception {
        when(dao.readByCredentials(not(eq("username")), eq("password"))).thenReturn(null);

        service.read("invalid username", "password");
    }

    @Test(expected = InvalidCredentialsException.class)
    public void read_invalidPassword_throwsException() throws Exception {
        when(dao.readByCredentials(eq("username"), not(eq("password")))).thenReturn(null);

        service.read("username", "invalid password");
    }

}