package com.besedo.processing.el.expressions;

import com.besedo.processing.el.Context;
import com.besedo.processing.el.Expression;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;


/**
 * Lazy evaluated boolean expression
 */
public class BooleanAlgebraExpression implements Expression<Boolean> {

    private final Operation operation;
    private final Expression<Boolean> left;
    private final Expression<Boolean> right;

    public BooleanAlgebraExpression(String operation, Expression<Boolean> left, Expression<Boolean> right) {
        this.operation = Operation.create(operation);
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean evaluate(Context context) {
        return operation.apply(left.evaluate(context), () -> right.evaluate(context));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanAlgebraExpression that = (BooleanAlgebraExpression) o;
        return operation == that.operation &&
                Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, left, right);
    }

    @Override
    public String toString() {
        return "BooleanAlgebraExpression{" +
                "operation=" + operation +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    private enum Operation implements BiFunction<Boolean, Supplier<Boolean>, Boolean> {
        AND((a, b) -> a && b.get()),
        OR((a, b) -> a || b.get());

        private final BiFunction<Boolean, Supplier<Boolean>, Boolean> operation;

        Operation(BiFunction<Boolean, Supplier<Boolean>, Boolean> operation) {
            this.operation = operation;
        }

        public Boolean apply(Boolean aBoolean, Supplier<Boolean> booleanSupplier) {
            return operation.apply(aBoolean, booleanSupplier);
        }

        private static Operation create(String operation) {
            switch (operation.toUpperCase()) {
                case "AND" : return Operation.AND;
                case "OR" : return Operation.OR;
                default: throw new IllegalArgumentException("Unsupported boolean operation: " + operation);
            }
        }
    }
}
