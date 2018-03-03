package nl.robinlaugs.kwetter.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Robin Laugs
 */
public class UserTest {

    @Test
    public void initialize_initializesFollowings() {
        User user = new User();

        assertThat(user.getFollowings(), is(notNullValue()));
    }

    @Test
    public void initialize_initializesFollowers() {
        User user = new User();

        assertThat(user.getFollowers(), is(notNullValue()));
    }

    @Test
    public void initialize_initializesMessages() {
        User user = new User();

        assertThat(user.getMessages(), is(notNullValue()));
    }

    @Test
    public void initialize_initializesLiked() {
        User user = new User();

        assertThat(user.getLiked(), is(notNullValue()));
    }

    @Test
    public void initialize_initializesMentioned() {
        User user = new User();

        assertThat(user.getMentioned(), is(notNullValue()));
    }

    @Test
    public void setName_validName_assignsName() {
        User user = new User();
        user.setName("name");

        assertThat(user.getName(), is("name"));
    }

    @Test
    public void setAvatar_validAvatar_assignsAvatar() {
        User user = new User();
        user.setAvatar("avatar");

        assertThat(user.getAvatar(), is("avatar"));
    }

    @Test
    public void setLocation_validLocation_assignsLocation() {
        User user = new User();
        user.setLocation("location");

        assertThat(user.getLocation(), is("location"));
    }

    @Test
    public void setWebsite_validWebsite_assignsWebsite() {
        User user = new User();
        user.setWebsite("website");

        assertThat(user.getWebsite(), is("website"));
    }

    @Test
    public void setBio_validBio_assignsBio() {
        User user = new User();
        user.setBio("bio");

        assertThat(user.getBio(), is("bio"));
    }

    @Test
    public void setAccount_validAccount_assignsAccount() {
        Account account = new Account();

        User user = new User();
        user.setAccount(account);

        assertThat(user.getAccount(), is(account));
    }

}
