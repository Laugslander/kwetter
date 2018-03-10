package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.ExceptionDto;
import nl.robinlaugs.kwetter.api.dto.MessageDto;
import nl.robinlaugs.kwetter.api.dto.UserDto;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.UserService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @InjectMocks
    private UserResource resource;

    @Mock
    private UserService service;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getAccounts_getsAccounts() {
        User user1 = new User();
        user1.setAccount(new Account());

        User user2 = new User();
        user2.setAccount(new Account());

        Collection<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(service.readAll()).thenReturn(users);

        Response response = resource.getUsers();

        Collection<UserDto> dto = ((Collection<UserDto>) response.getEntity());

        assertThat(response.getStatus(), is(200));
        assertThat(dto.size(), is(2));
    }

    @Test
    public void getUser_validUserId_getsUser() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setAccount(new Account());

        when(service.read(anyLong())).thenReturn(user);

        Response response = resource.getUser(1L);

        UserDto dto = (UserDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getName(), is("name"));
    }

    @Test
    public void patchUser_validUser_patchesAndReturnsUser() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setAccount(new Account());

        when(service.read(anyLong())).thenReturn(user);

        Response response = resource.patchUser(user);

        UserDto dto = (UserDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getName(), is("name"));
    }

    @Test
    public void patchUser_invalidAccount_returnsException() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setAccount(new Account());

        doThrow(Exception.class).when(service).update(any(User.class));

        Response response = resource.patchUser(user);

        ExceptionDto dto = (ExceptionDto) response.getEntity();

        assertThat(response.getStatus(), is(400));
        assertThat(dto, instanceOf(ExceptionDto.class));
    }

    @Test
    public void follow_validUserIdAndFollowerUserId_followersFollowsUser() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("name1");
        user1.setAccount(new Account());

        User user2 = new User();
        user2.setId(2L);
        user2.setName("name2");
        user2.setAccount(new Account());

        when(service.read(anyLong())).thenReturn(user1).thenReturn(user2).thenReturn(user1);

        Response response = resource.follow(1L, 2L);

        UserDto dto = (UserDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getName(), is("name1"));
    }

    @Test
    public void unfollow_validUserIdAndFollowerUserId_unfollowerUnfollowersUser() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("name1");
        user1.setAccount(new Account());

        User user2 = new User();
        user2.setId(2L);
        user2.setName("name2");
        user2.setAccount(new Account());

        when(service.read(anyLong())).thenReturn(user1).thenReturn(user2).thenReturn(user1);

        Response response = resource.unfollow(1L, 2L);

        UserDto dto = (UserDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getName(), is("name1"));
    }

    @Test
    public void timelineMessages_validUserId_getsTimelineMessages() {
        User user1 = new User();
        user1.setId(1L);
        user1.setAccount(new Account());

        Message message1 = new Message();
        message1.setAuthor(user1);

        Message message2 = new Message();
        message2.setAuthor(user1);

        Collection<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        when(service.read(anyLong())).thenReturn(user1);
        when(service.readAll(any(User.class))).thenReturn(messages);

        Response response = resource.timelineMessages(1L);

        Collection<MessageDto> dto = (Collection<MessageDto>) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.size(), is(2));
    }

    @Test
    public void personalMessages_validUserId_getsPeronalMessages() {
        User user1 = new User();
        user1.setId(1L);
        user1.setAccount(new Account());

        Message message1 = new Message();
        message1.setAuthor(user1);

        Message message2 = new Message();
        message2.setAuthor(user1);

        Collection<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        when(service.read(anyLong())).thenReturn(user1);
        when(service.readOwn(any(User.class), anyInt())).thenReturn(messages);

        Response response = resource.personalMessages(1L);

        Collection<MessageDto> dto = (Collection<MessageDto>) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.size(), is(2));
    }

}