package be.sonck.mtg.rules.impl.service;

import be.sonck.mtg.rules.api.model.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Map;

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
                .build());

        Rule rule = repository().getRule("1.");
        validateRule(rule, RULE_1);

        Collection<Rule> children = rule.getChildren();
        assertFalse(isEmpty(children));
    }

    private void expectGetRules(Map<String, String> map) {
        Mockito.when(rulesParser.getRules()).thenReturn(map);
    }

    private void validateRule(Rule rule, String ruleText) {
        assertNotNull(rule);
        assertThat(rule.getText(), is(ruleText));
    }

    private RulesRepository repository() {
        return new RulesRepository(rulesParser);
    }
}