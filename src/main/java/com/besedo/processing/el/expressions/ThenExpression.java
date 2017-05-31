package com.besedo.processing.el.expressions;

import com.besedo.processing.el.Context;
import com.besedo.processing.el.Expression;

import java.util.Objects;
import java.util.Optional;


public class ThenExpression<T> implements Expression<Optional<T>> {

    private final Expression<Boolean> condition;
    private final Expression<T> right;

    public ThenExpression(Expression<Boolean> condition, Expression<T> right) {
        this.condition = condition;
        this.right = right;
    }

    @Override
    public Optional<T> evaluate(Context context) {
        if (condition.evaluate(context)) {
            // right expression shouldn't return null
            return Optional.of(right.evaluate(context));
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThenExpression<?> that = (ThenExpression<?>) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, right);
    }

    @Override
    public String toString() {
        return "ThenExpression{" +
                "condition=" + condition +
                ", right=" + right +
                '}';
    }
}
