package nl.robinlaugs.kwetter.service.interceptor;

import java.util.Collection;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

/**
 * @author Robin Laugs
 */
public abstract class BaseParsingInterceptor {

    private static final String REGEX_SPLIT_BY_WORDS = "\\s+";
    private static final String REGEX_PARSE_BY_TAG = "^\\%s(\\w)+$";

    Collection<String> splitByWords(String text) {
        return asList(text.split(REGEX_SPLIT_BY_WORDS));
    }

    Collection<String> parse(Collection<String> words, char parser) {
        return words.stream()
                .filter(w -> w.matches(format(REGEX_PARSE_BY_TAG, parser)))
                .map(m -> m.substring(1))
                .collect(toSet());
    }

}
