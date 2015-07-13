package be.sonck;

import com.google.common.collect.Iterables;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
    public void getTableOfContents() {
        Iterable<String> tableOfContents = rulesParser.getTableOfContents();

        assertNotNull(tableOfContents);

        assertThat(Iterables.getFirst(tableOfContents, ""), is("1. Game Concepts"));
        assertThat(Iterables.getLast(tableOfContents), is("905. Conspiracy Draft"));
    }
}