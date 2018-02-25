package nl.robinlaugs.kwetter.domain;

import org.junit.Test;

import static nl.robinlaugs.kwetter.domain.Role.USER;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Robin Laugs
 */
public class AccountTest {

    @Test
    public void initialize_assignsRole() {
        Account account = Account.builder().build();

        Role actual = account.getRole();
        Role expected = USER;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void initialize_assignsUser() {
        Account account = Account.builder().build();

        User actual = account.getUser();

        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void setUsername_validUsername_assignsUsername() {
        Account account = Account.builder().username("username").build();

        String actual = account.getUsername();
        String expected = "username";

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setPassword_validPassword_assignsPassword() {
        Account account = Account.builder().password("password").build();

        String actual = account.getPassword();
        String expected = "password";

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setRole_validRole_assignsPassword() {
        Account account = Account.builder().role(USER).build();

        Role actual = account.getRole();
        Role expected = USER;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setUser_validUser_assignsUser() {
        User user = User.builder().build();
        Account account = Account.builder().user(user).build();

        User actual = account.getUser();
        User expected = user;

        assertThat(actual, is(equalTo(expected)));
    }

}