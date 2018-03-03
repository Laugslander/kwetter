package nl.robinlaugs.kwetter.service.interceptor;

import lombok.extern.java.Log;
import nl.robinlaugs.kwetter.domain.Message;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.Map;

import static java.util.logging.Level.WARNING;

/**
 * @author Robin Laugs
 */
@Interceptor
@ParsingInterceptor
@Log
public class TextParsingInterceptor {

    private static final Map<String, String> WORDS_TO_REPLACE = new HashMap<String, String>() {{
        put("vet", "hard");
        put("cool", "dik");
    }};

    @AroundInvoke
    public Object parseMessage(InvocationContext context) {
        Object[] parameters = context.getParameters();
        Message message = (Message) parameters[0];

        String text = replaceWords(message.getText());

        message.setText(text);

        parameters[0] = message;
        context.setParameters(parameters);

        try {
            return context.proceed();
        } catch (Exception e) {
            log.log(WARNING, "An error occurred while parsing the message for words to replace", e);
            return null;
        }
    }

    public String replaceWords(String text) {
        return WORDS_TO_REPLACE.entrySet().stream()
                .reduce(text, (k, v) -> k.replace(v.getKey(), v.getValue()), (k, v) -> null);
    }

}
