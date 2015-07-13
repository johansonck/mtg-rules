package be.sonck;

import com.google.common.collect.Iterators;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by johansonck on 12/07/15.
 */
public class RulesParserTest {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-beans.xml");

    private RulesParser rulesParser = (RulesParser) applicationContext.getBean("rulesParser");

    @Test
    public void getEffectiveDate() {
        assertThat(rulesParser.getEffectiveDate(), is("These rules are effective as of March 27, 2015."));
    }

    @Test
    public void getRulesOverview() {
        Map<String, String> map = rulesParser.getRulesOverview();

        assertNotNull(map);

        Set<String> keys = map.keySet();
        Iterator<String> keyIterator = keys.iterator();

        assertTrue(keyIterator.hasNext());
        validate(map, keyIterator.next(), "1.", "Game Concepts");

        assertTrue(keyIterator.hasNext());
        validate(map, keyIterator.next(), "100.", "General");

        validate(map, Iterators.getLast(keyIterator), "905.", "Conspiracy Draft");
    }

    @Test
    public void getRules() {
        Map<String, String> map = rulesParser.getRules();

        assertNotNull(map);

        Set<String> keys = map.keySet();
        Iterator<String> keyIterator = keys.iterator();

        assertTrue(keyIterator.hasNext());
        validate(map, keyIterator.next(), "1.", "Game Concepts");

        assertTrue(keyIterator.hasNext());
        validate(map, keyIterator.next(), "100.", "General");

        validate(map, Iterators.getLast(keyIterator), "905.6.", "Once the starting player has been determined, each player sets his or her life total to 20 and draws a hand of seven cards.");
    }

    private void validate(Map<String, String> map, String key, String expectedKey, String expectedValue) {
        assertThat(key, is(expectedKey));
        assertThat(map.get(key), is(expectedValue));
    }
}