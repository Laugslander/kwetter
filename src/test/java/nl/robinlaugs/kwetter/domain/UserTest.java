package nl.robinlaugs.kwetter.domain;

import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Robin Laugs
 */
public class UserTest {

    @Test
    public void setName_validName_assignsName() {
        User user = User.builder().name("name").build();

        String actual = user.getName();
        String expected = "name";

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setAvatar_validAvatar_assignsAvatar() {
        User user = User.builder().avatar("avatar").build();

        String actual = user.getAvatar();
        String expected = "avatar";

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setLocation_validLocation_assignsLocation() {
        User user = User.builder().location("location").build();

        String actual = user.getLocation();
        String expected = "location";

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setWebsite_validWebsite_assignsWebsite() {
        User user = User.builder().website("website").build();

        String actual = user.getWebsite();
        String expected = "website";

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setBio_validBio_assignsBio() {
        User user = User.builder().bio("bio").build();

        String actual = user.getBio();
        String expected = "bio";

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setAccount_validAccount_assignsAccount() {
        Account account = Account.builder().build();
        User user = User.builder().account(account).build();

        Account actual = user.getAccount();
        Account expected = account;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addFollowings_validFollowings_assignsFollowings() {
        User following = User.builder().build();
        Collection<User> followings = asList(following);
        User user = User.builder().followings(followings).build();

        Collection<User> actual = user.getFollowings();
        Collection<User> expected = followings;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addFollowing_validFollowing_addsFollowing() {
        User following = User.builder().build();
        User user = User.builder().following(following).build();

        assertThat(user.getFollowings().contains(following), is(true));
    }

    @Test
    public void addFollowers_validFollowers_assignsFollowers() {
        User follower = User.builder().build();
        Collection<User> followers = asList(follower);
        User user = User.builder().followers(followers).build();

        Collection<User> actual = user.getFollowers();
        Collection<User> expected = followers;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addFollower_validFollower_addsFollower() {
        User follower = User.builder().build();
        User user = User.builder().follower(follower).build();

        assertThat(user.getFollowers().contains(follower), is(true));
    }

    @Test
    public void addMessages_validMessages_assignsMessages() {
        Message message = Message.builder().build();
        Collection<Message> messages = asList(message);
        User user = User.builder().messages(messages).build();

        Collection<Message> actual = user.getMessages();
        Collection<Message> expected = messages;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addMessage_validMessage_addsMessage() {
        Message message = Message.builder().build();
        User user = User.builder().message(message).build();

        assertThat(user.getMessages().contains(message), is(true));
    }

    @Test
    public void addLiked_validLiked_assignedLiked() {
        Message like = Message.builder().build();
        Collection<Message> liked = asList(like);
        User user = User.builder().liked(liked).build();

        Collection<Message> actual = user.getLiked();
        Collection<Message> expected = liked;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addLike_validLike_assignedLike() {
        Message like = Message.builder().build();
        User user = User.builder().like(like).build();

        assertThat(user.getLiked().contains(like), is(true));
    }

    @Test
    public void addMentioned_validMentioned_assignsMentioned() {
        Message mention = Message.builder().build();
        Collection<Message> mentioned = asList(mention);
        User user = User.builder().mentioned(mentioned).build();

        Collection<Message> actual = user.getMentioned();
        Collection<Message> expected = mentioned;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addMention_validMention_addsMention() {
        Message mention = Message.builder().build();
        User user = User.builder().mention(mention).build();

        assertThat(user.getMentioned().contains(mention), is(true));
    }

}
