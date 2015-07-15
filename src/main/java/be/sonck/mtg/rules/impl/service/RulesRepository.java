package be.sonck.mtg.rules.impl.service;

import be.sonck.mtg.rules.api.model.Rule;

/**
 * Created by johansonck on 14/07/15.
 */
public class RulesRepository {

    private final RulesParser rulesParser;


    public RulesRepository(RulesParser rulesParser) {
        this.rulesParser = rulesParser;
    }

    public Rule getRule(String ruleId) {
        String ruleText = rulesParser.getRules().get(ruleId);

        return new Rule(ruleId, ruleText);
    }

    private void initialize() {

    }
}
