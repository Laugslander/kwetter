package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.AccountDto;
import nl.robinlaugs.kwetter.api.dto.ExceptionDto;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountResourceTest {

    @InjectMocks
    private AccountResource resource;

    @Mock
    private AccountService service;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getAccounts_getsAccounts() {
        Collection<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());

        when(service.readAll()).thenReturn(accounts);

        Response response = resource.getAccounts();

        Collection<AccountDto> dto = ((Collection<AccountDto>) response.getEntity());

        assertThat(response.getStatus(), is(200));
        assertThat(dto.size(), is(2));
    }

    @Test
    public void getAccount_validAccountId_getsAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");

        when(service.read(anyLong())).thenReturn(account);

        Response response = resource.getAccount(1L);

        AccountDto dto = (AccountDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getUsername(), is("username"));
    }

    @Test
    public void postAccount_validAccount_postsAndReturnsAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");

        when(service.read(anyLong())).thenReturn(account);

        Response response = resource.postAccount(account);

        AccountDto dto = (AccountDto) response.getEntity();

        assertThat(response.getStatus(), is(201));
        assertThat(dto.getUsername(), is("username"));
    }

    @Test
    public void postAccount_invalidAccount_returnsException() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");

        doThrow(Exception.class).when(service).create(any(Account.class));

        Response response = resource.postAccount(account);

        ExceptionDto dto = (ExceptionDto) response.getEntity();

        assertThat(response.getStatus(), is(400));
        assertThat(dto, instanceOf(ExceptionDto.class));
    }

    @Test
    public void patchAccount_validAccount_patchesAndReturnsAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");

        when(service.read(anyLong())).thenReturn(account);

        Response response = resource.patchAccount(account);

        AccountDto dto = (AccountDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getUsername(), is("username"));
    }

    @Test
    public void patchAccount_invalidAccount_returnsException() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");

        doThrow(Exception.class).when(service).update(any(Account.class));

        Response response = resource.patchAccount(account);

        ExceptionDto dto = (ExceptionDto) response.getEntity();

        assertThat(response.getStatus(), is(400));
        assertThat(dto, instanceOf(ExceptionDto.class));
    }

}