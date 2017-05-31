package com.besedo.processing.el.expressions;


import com.besedo.processing.el.Expression;
import com.besedo.processing.el.expressions.util.ConstantContext;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ThenExpressionTest {

    private Expression<Boolean> condition = new VariableBindingExpression<>("name");
    private Expression<String> success = new ConstantExpression<>("success");
    private Expression<String> nullExpression = new ConstantExpression<>(null);

    @Test(expected = NullPointerException.class)
    public void testNpe() {
        ThenExpression<String> thenExpression = new ThenExpression<>(condition, nullExpression);
        thenExpression.evaluate(new ConstantContext(true));
    }

    @Test
    public void testCondition() {
        ThenExpression<String> thenExpression = new ThenExpression<>(condition, success);
        assertEquals(Optional.of("success"), thenExpression.evaluate(new ConstantContext(true)));
        assertEquals(Optional.empty(), thenExpression.evaluate(new ConstantContext(false)));
    }

}
