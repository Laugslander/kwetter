package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.exception.DuplicateUsernameException;
import nl.robinlaugs.kwetter.exception.InvalidCredentialsException;
import nl.robinlaugs.kwetter.exception.NullArgumentException;
import nl.robinlaugs.kwetter.exception.UnknownEntityException;
import nl.robinlaugs.kwetter.persistence.AccountDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountMainServiceTest {

    @InjectMocks
    private AccountMainService service;

    @Mock
    private AccountDao dao;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void create_validAccount_callsDao() throws Exception {
        Account account = new Account("username", "password");

        service.create(account);

        verify(dao).create(account);
    }

    @Test(expected = NullArgumentException.class)
    public void create_accountWithNullUsername_throwsException() throws Exception {
        Account account = new Account();
        account.setPassword("password");

        service.create(account);
    }

    @Test(expected = NullArgumentException.class)
    public void create_accountWithNullPassword_throwsException() throws Exception {
        Account account = new Account();
        account.setUsername("username");

        service.create(account);
    }

    @Test(expected = DuplicateUsernameException.class)
    public void create_accountWithDuplicateUsername_throwsException() throws Exception {
        Account account = new Account("username", "password");

        when(dao.readByUsername("username")).thenReturn(account);

        service.create(account);
    }

    @Test
    public void update_validAccountIdAndUpdate_callsDao() throws Exception {
        Account account = new Account("username", "password");

        when(service.read(1L)).thenReturn(account);

        service.update(1L, account);

        verify(dao).update(account);
    }

    @Test(expected = UnknownEntityException.class)
    public void update_unknownAccountId_throwsException() throws Exception {
        Account account = new Account("username", "password");

        when(dao.read(1L)).thenReturn(null);

        service.update(1L, account);
    }

    @Test(expected = DuplicateUsernameException.class)
    public void update_accountWithDuplicateUsername_throwsException() throws Exception {
        Account account = new Account("username", "password");

        when(dao.read(1L)).thenReturn(account);
        when(dao.readByUsername("username")).thenReturn(account);

        service.update(1L, account);
    }

    @Test
    public void read_validUsername_callsDao() {
        Account account = new Account();
        account.setUsername("username");

        when(dao.readByUsername("username")).thenReturn(account);

        service.read("username");

        verify(dao).readByUsername("username");
    }

    @Test
    public void read_validCredentials_callsDao() throws Exception {
        Account account = new Account("username", "password");

        when(dao.readByCredentials("username", "password")).thenReturn(account);

        service.read("username", "password");

        verify(dao).readByCredentials("username", "password");
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