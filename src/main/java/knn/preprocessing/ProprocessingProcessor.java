package knn.preprocessing;

import java.util.ArrayList;
import java.util.List;

public class ProprocessingProcessor {
    private List<PreprocessingRule> rules;

    public ProprocessingProcessor() {
        rules = new ArrayList<PreprocessingRule>();
    }

    public void addRule(PreprocessingRule rule){
        rules.add(rule);
    }

    public String processRules(String text){
        for (PreprocessingRule rule : rules) {
            text = rule.applyTo(text);
        }

        return text;
    }
}
