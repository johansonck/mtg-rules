package be.sonck.mtg.rules.impl.service;

import be.sonck.mtg.rules.api.model.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by johansonck on 14/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class RulesRepositoryTest {

    private static final String RULE_1 = "rule1";
    private static final String RULE_2 = "rule2";
    private static final String RULE_3 = "rule3";

    @Mock
    private RulesParser rulesParser;

    @Test
    public void getRule() {
        expectGetRules(new StringHashMapBuilder()
                .entry("1.", RULE_1)
                .entry("2.", RULE_2)
                .build());

        validateRule(repository().getRule("1."), RULE_1);
        validateRule(repository().getRule("2."), RULE_2);
    }

    @Test
    public void getRuleWithChildren() {
        expectGetRules(new StringHashMapBuilder()
                .entry("1.", RULE_1)
                .entry("100.", RULE_2)
                .entry("101.", RULE_3)
                .build());

        Optional<Rule> optionalRule = repository().getRule("1.");
        validateRule(optionalRule, RULE_1);

        Collection<Rule> children = optionalRule.get().getChildren();
        assertFalse(isEmpty(children));
        assertThat(children.size(), is(2));

        Iterator<Rule> ruleIterator = children.iterator();

        Rule child = ruleIterator.next();
        assertThat(child.getId(), is("100."));
        assertThat(child.getText(), is(RULE_2));

        child = ruleIterator.next();
        assertThat(child.getId(), is("101."));
        assertThat(child.getText(), is(RULE_3));
    }

    private void expectGetRules(Map<String, String> map) {
        Mockito.when(rulesParser.getRules()).thenReturn(map);
    }

    private void validateRule(Optional<Rule> optionalRule, String ruleText) {
        assertTrue(optionalRule.isPresent());
        assertThat(optionalRule.get().getText(), is(ruleText));
    }

    private RulesRepository repository() {
        return new RulesRepository(rulesParser);
    }
}