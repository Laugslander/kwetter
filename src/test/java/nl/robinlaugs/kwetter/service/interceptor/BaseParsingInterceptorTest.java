package nl.robinlaugs.kwetter.service.interceptor;

import nl.robinlaugs.kwetter.tester.BaseParsingInterceptorTester;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * @author Robin Laugs
 */
public class BaseParsingInterceptorTest {

    private BaseParsingInterceptor interceptor;

    @Before
    public void setUp() {
        interceptor = new BaseParsingInterceptorTester();
    }

    @Test
    public void splitByWords_validString_splitsInputStringByWords() {
        String text = "word1 word2 word3";

        Collection<String> actual = interceptor.splitByWords(text);
        Collection<String> expected = asList("word1", "word2", "word3");

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void parse_validWordsAndParser_parsesWords() {
        Collection<String> words = asList("word1", "$word2", "word3");
        char parser = '$';

        Collection<String> actual = interceptor.parse(words, parser);

        assertThat(actual, contains("word2"));
    }

}