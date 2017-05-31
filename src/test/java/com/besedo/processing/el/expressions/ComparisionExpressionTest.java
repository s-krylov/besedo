package com.besedo.processing.el.expressions;


import com.besedo.processing.el.Expression;
import com.besedo.processing.el.expressions.util.ConstantContext;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComparisionExpressionTest {

    private final Expression<Comparable> one = new ConstantExpression<>(1);
    private final Expression<Comparable> variable = new VariableBindingExpression<>("name");

    @Test
    public void testGt() {
        ComparisionExpression comparisionExpression = new ComparisionExpression(">", variable, one);
        assertFalse(comparisionExpression.evaluate(new ConstantContext(0)));
        assertFalse(comparisionExpression.evaluate(new ConstantContext(1)));
        assertTrue(comparisionExpression.evaluate(new ConstantContext(2)));
    }

    @Test
    public void testGe() {
        ComparisionExpression comparisionExpression = new ComparisionExpression(">=", variable, one);
        assertFalse(comparisionExpression.evaluate(new ConstantContext(0)));
        assertTrue(comparisionExpression.evaluate(new ConstantContext(1)));
        assertTrue(comparisionExpression.evaluate(new ConstantContext(2)));
    }

    @Test
    public void testEq() {
        ComparisionExpression comparisionExpression = new ComparisionExpression("=", variable, one);
        assertFalse(comparisionExpression.evaluate(new ConstantContext(0)));
        assertTrue(comparisionExpression.evaluate(new ConstantContext(1)));
        assertFalse(comparisionExpression.evaluate(new ConstantContext(2)));
    }

    @Test
    public void testLe() {
        ComparisionExpression comparisionExpression = new ComparisionExpression("=<", variable, one);
        assertTrue(comparisionExpression.evaluate(new ConstantContext(0)));
        assertTrue(comparisionExpression.evaluate(new ConstantContext(1)));
        assertFalse(comparisionExpression.evaluate(new ConstantContext(2)));
    }

    @Test
    public void testLt() {
        ComparisionExpression comparisionExpression = new ComparisionExpression("<", variable, one);
        assertTrue(comparisionExpression.evaluate(new ConstantContext(0)));
        assertFalse(comparisionExpression.evaluate(new ConstantContext(1)));
        assertFalse(comparisionExpression.evaluate(new ConstantContext(2)));
    }

}
