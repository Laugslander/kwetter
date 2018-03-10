package nl.robinlaugs.kwetter.service.interceptor;

import lombok.extern.java.Log;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.AccountService;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Collection;
import java.util.Objects;

import static java.util.logging.Level.WARNING;
import static java.util.stream.Collectors.toSet;

/**
 * @author Robin Laugs
 */
@Interceptor
@ParsingInterceptor
@Log
public class MentionParsingInterceptor extends BaseParsingInterceptor {

    private static final char TAG_MENTION = '@';

    @Inject
    private AccountService service;

    @AroundInvoke
    public Object parseMessage(InvocationContext context) {
        Object[] parameters = context.getParameters();
        Message message = (Message) parameters[0];

        Collection<String> words = splitByWords(message.getText());
        Collection<User> mentions = parseMentions(words);

        message.setMentions(mentions);

        parameters[0] = message;
        context.setParameters(parameters);

        try {
            return context.proceed();
        } catch (Exception e) {
            log.log(WARNING, "An error occurred while parsing the message for mentions", e);
            return null;
        }
    }

    Collection<User> parseMentions(Collection<String> words) {
        Collection<String> mentions = parse(words, TAG_MENTION);

        return mentions.stream()
                .map(m -> service.read(m))
                .filter(Objects::nonNull)
                .map(Account::getUser)
                .collect(toSet());
    }

}
