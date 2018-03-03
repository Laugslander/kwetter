package nl.robinlaugs.kwetter.service.interceptor;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Robin Laugs
 */
public class TextParsingInterceptorTest {

    private TextParsingInterceptor interceptor;

    @Before
    public void setUp() {
        interceptor = new TextParsingInterceptor();
    }

    @Test
    public void replaceWords_validText_replacesWords() {
        String actual = interceptor.replaceWords("vet string cool");
        String expected = "hard string dik";

        assertThat(actual, is(equalTo(expected)));
    }

}