package nl.robinlaugs.kwetter.service.interceptor;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.main.AccountMainService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class MentionParsingInterceptorTest {

    @InjectMocks
    private MentionParsingInterceptor interceptor;

    @Mock
    private AccountMainService service;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void parseMentions_validWords_returnsMentions() {
        Collection<String> mentions = asList("@mention1", "mention2", "mention3");
        Account account = new Account();
        account.setUsername("mention1");

        when(service.read("mention1")).thenReturn(account);

        Collection<User> actual = interceptor.parseMentions(mentions);

        assertThat(actual, contains(account.getUser()));
    }

}