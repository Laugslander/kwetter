package nl.robinlaugs.kwetter.domain;

import org.junit.Test;

import static nl.robinlaugs.kwetter.domain.Role.MODERATOR;
import static nl.robinlaugs.kwetter.domain.Role.USER;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Robin Laugs
 */
public class AccountTest {

    @Test
    public void initialize_initializesRole() {
        Account account = new Account();

        assertThat(account.getRole(), is(USER));
    }

    @Test
    public void initialize_initializesUser() {
        Account account = new Account();

        assertThat(account.getUser(), is(instanceOf(User.class)));
    }

    @Test
    public void setUsername_validUsername_assignsUsername() {
        Account account = new Account();
        account.setUsername("username");

        assertThat(account.getUsername(), is("username"));
    }

    @Test
    public void setPassword_validPassword_assignsPassword() {
        Account account = new Account();
        account.setPassword("password");

        assertThat(account.getPassword(), is("password"));
    }

    @Test
    public void setRole_validRole_assignsPassword() {
        Account account = new Account();
        account.setRole(MODERATOR);

        assertThat(account.getRole(), is(MODERATOR));
    }

    @Test
    public void setUser_validUser_assignsUser() {
        User user = new User();

        Account account = new Account();
        account.setUser(user);

        assertThat(account.getUser(), is(user));
    }

}