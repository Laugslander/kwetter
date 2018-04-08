package nl.robinlaugs.kwetter.api;

import nl.robinlaugs.kwetter.api.dto.ExceptionDto;
import nl.robinlaugs.kwetter.api.dto.MessageDto;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.MessageService;
import nl.robinlaugs.kwetter.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageResourceTest {

    @InjectMocks
    private MessageResource resource;

    @Mock
    private MessageService messageService;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getMessages_getsMessages() {
        Message message1 = new Message();
        message1.setAuthor(new User());

        Message message2 = new Message();
        message2.setAuthor(new User());

        Collection<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        when(messageService.readAll()).thenReturn(messages);

        Response response = resource.getMessages();

        Collection<MessageDto> dto = ((Collection<MessageDto>) response.getEntity());

        assertThat(response.getStatus(), is(200));
        assertThat(dto.size(), is(2));
    }

    @Test
    public void getMessage_validMessageId_getsMessage() throws Exception {
        Message message = new Message("text");
        message.setId(1L);
        message.setAuthor(new User());

        when(messageService.read(1L)).thenReturn(message);

        Response response = resource.getMessage(1L);

        MessageDto dto = (MessageDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getText(), is("text"));
    }

    @Test
    public void getMessage_unknownAccountId_returnsException() throws Exception {
        doThrow(Exception.class).when(messageService).read(1L);

        Response response = resource.getMessage(1L);

        ExceptionDto dto = (ExceptionDto) response.getEntity();

        assertThat(response.getStatus(), is(400));
        assertThat(dto, instanceOf(ExceptionDto.class));
    }

    @Ignore
    @Test
    public void postMessage_validMessage_postsAndReturnsMessage() throws Exception {
        Message message = new Message("text");
        message.setId(1L);
        message.setAuthor(new User());

        Response response = resource.postMessage(message);

        MessageDto dto = (MessageDto) response.getEntity();

        verify(messageService).create(message);

        assertThat(response.getStatus(), is(201));
        assertThat(dto.getText(), is("text"));
    }

    @Ignore
    @Test
    public void postMessage_invalidMessage_returnsException() throws Exception {
        Message message = new Message("text");
        message.setId(1L);
        message.setAuthor(new User());

        doThrow(Exception.class).when(messageService).create(message);

        Response response = resource.postMessage(message);

        ExceptionDto dto = (ExceptionDto) response.getEntity();

        assertThat(response.getStatus(), is(400));
        assertThat(dto, instanceOf(ExceptionDto.class));
    }

    @Test
    public void like_validMessageIdAndUserId_UserLikesMessage() throws Exception {
        Message message = new Message("text");
        message.setId(1L);
        message.setAuthor(new User());

        User user = new User();
        user.setId(2L);
        user.setName("name");
        user.setAccount(new Account());

        when(messageService.read(1L)).thenReturn(message);
        when(userService.read(2L)).thenReturn(user);

        Response response = resource.like(1L, 2L);

        MessageDto dto = (MessageDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getText(), is("text"));
    }

    @Test
    public void unlike_validMessageIdAndUserId_UsersUnlikesMessage() throws Exception {
        Message message = new Message("text");
        message.setId(1L);
        message.setAuthor(new User());

        User user = new User();
        user.setId(2L);
        user.setName("name");
        user.setAccount(new Account());

        when(messageService.read(1L)).thenReturn(message);
        when(userService.read(2L)).thenReturn(user);

        Response response = resource.unlike(1L, 2L);

        MessageDto dto = (MessageDto) response.getEntity();

        assertThat(response.getStatus(), is(200));
        assertThat(dto.getText(), is("text"));
    }

    @Test
    public void search_validSearchString_returnsMessages() {
        Message message = new Message("text");
        message.setId(1L);
        message.setAuthor(new User());

        Collection<Message> messages = new ArrayList<>();
        messages.add(message);

        when(messageService.search("text")).thenReturn(messages);

        Response response = resource.searchMessages("text");

        Collection<MessageDto> dto = ((Collection<MessageDto>) response.getEntity());

        assertThat(response.getStatus(), is(200));
        assertThat(dto.size(), is(1));
    }


}