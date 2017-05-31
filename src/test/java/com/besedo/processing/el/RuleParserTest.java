package com.besedo.processing.el;


import com.besedo.processing.el.expressions.BooleanAlgebraExpression;
import com.besedo.processing.el.expressions.ComparisionExpression;
import com.besedo.processing.el.expressions.ConstantExpression;
import com.besedo.processing.el.expressions.ContainsExpression;
import com.besedo.processing.el.expressions.ThenExpression;
import com.besedo.processing.el.expressions.VariableBindingExpression;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class RuleParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNoThen() {
        RuleParser.parseRule("$price = 0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoBooleanCondition() {
        RuleParser.parseRule("20 THEN red");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyCondition() {
        RuleParser.parseRule("  THEN red");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoBoolean() {
        RuleParser.parseRule("$price >= 10000 $description NOTCONTAINS (bmw) THEN red");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownOperator() {
        RuleParser.parseRule("$price === 10000 THEN red");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContainsWithoutBraces() {
        RuleParser.parseRule("$description NOTCONTAINS bmw THEN red");
    }

    @Test
    public void testParsedExpression() {
        VariableBindingExpression<Comparable> title = new VariableBindingExpression<>("price");
        ComparisionExpression eq = new ComparisionExpression("=", title, title);
        VariableBindingExpression<String> description = new VariableBindingExpression<>("description");
        ContainsExpression containsExpression = new ContainsExpression(true, description, Arrays.asList("details", "more"));
        BooleanAlgebraExpression and = new BooleanAlgebraExpression("and", eq, containsExpression);
        ThenExpression<String> then = new ThenExpression<>(and, new ConstantExpression<>("matched"));
        assertEquals(then, RuleParser.parseRule("$price = $price AND $description CONTAINS (details, more) THEN matched"));
    }
}
