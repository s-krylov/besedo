package com.besedo.processing;


import com.besedo.processing.el.expressions.ComparisionExpression;
import com.besedo.processing.el.expressions.ConstantExpression;
import com.besedo.processing.el.expressions.ThenExpression;
import com.besedo.processing.el.expressions.VariableBindingExpression;
import com.besedo.processing.json.Document;
import com.besedo.processing.json.DocumentBuilder;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class RuleValidatorTest {

    @Test(expected = IllegalStateException.class)
    public void testNoRulesValidator() {
        RuleValidator ruleValidator = new RuleValidator(Collections.emptyList());
        ruleValidator.addStatisticConsumer((a, b) -> assertTrue(false));
        ruleValidator.accept(createDocument(1, 100, "dummy", "none"));
    }

    @Test(expected = IllegalStateException.class)
    public void testNoRulesApplied() {
        RuleValidator ruleValidator = new RuleValidator(createRules());
        ruleValidator.addStatisticConsumer((a, b) -> assertTrue(false));
        ruleValidator.accept(createDocument(1, 100, "dummy", "none"));
    }

    @Test
    public void testOneRuleMatch() {
        RuleValidator ruleValidator = new RuleValidator(createRules());
        ruleValidator.addStatisticConsumer((a, b) -> assertEquals("first", b));
        ruleValidator.accept(createDocument(1, 200, "qqq", "www"));

        ruleValidator = new RuleValidator(createRules());
        ruleValidator.addStatisticConsumer((a, b) -> assertEquals("second", b));
        ruleValidator.accept(createDocument(1, 99, "super title", "www"));
    }

    @Test
    public void testAllRulesMatch() {
        RuleValidator ruleValidator = new RuleValidator(createRules());
        ruleValidator.addStatisticConsumer((a, b) -> assertEquals("first", b));
        ruleValidator.accept(createDocument(1, 200, "super title", "www"));
    }

    private List<ThenExpression<String>> createRules() {
        return Arrays.asList(
                new ThenExpression<>(new ComparisionExpression(">", new VariableBindingExpression<>("price"), new ConstantExpression<>(new BigDecimal(100))),
                            new ConstantExpression<>("first")),
                new ThenExpression<>(new ComparisionExpression("=", new VariableBindingExpression<>("title"), new ConstantExpression<>("super title")),
                            new ConstantExpression<>("second")));

    }

    private Document createDocument(int id, float price, String title, String description) {
        return new DocumentBuilder()
                .setId(id)
                .setPrice(price)
                .setTitle(title)
                .setDescription(description)
                .build();
    }
}
