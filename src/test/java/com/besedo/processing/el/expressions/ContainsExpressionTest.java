package com.besedo.processing.el.expressions;


import com.besedo.processing.el.Expression;
import com.besedo.processing.el.expressions.util.ConstantContext;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ContainsExpressionTest {

    private final Expression<String> variable = new VariableBindingExpression<>("name");

    @Test
    public void testContains() {
        ContainsExpression containsExpression = new ContainsExpression(true, variable, Arrays.asList("first", "second"));
        assertTrue(containsExpression.evaluate(new ConstantContext("first")));
        assertTrue(containsExpression.evaluate(new ConstantContext("firstsecond")));
        assertFalse(containsExpression.evaluate(new ConstantContext("third")));
    }

    @Test
    public void testNotContains() {
        ContainsExpression containsExpression = new ContainsExpression(false, variable, Arrays.asList("first", "second"));
        assertFalse(containsExpression.evaluate(new ConstantContext("first")));
        assertFalse(containsExpression.evaluate(new ConstantContext("firstsecond")));
        assertTrue(containsExpression.evaluate(new ConstantContext("third")));
    }
}
