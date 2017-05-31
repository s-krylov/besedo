package com.besedo.processing.el.expressions;

import com.besedo.processing.el.Context;
import com.besedo.processing.el.Expression;

import java.util.Objects;
import java.util.function.Predicate;


public class ComparisionExpression implements Expression<Boolean> {

    private final Comparision comparision;
    private final Expression<Comparable> left;
    private final Expression<Comparable> right;

    public ComparisionExpression(String comparision, Expression<Comparable> left, Expression<Comparable> right) {
        this.comparision = Comparision.create(comparision);
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean evaluate(Context context) {
        return comparision.test(left.evaluate(context).compareTo(right.evaluate(context)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComparisionExpression that = (ComparisionExpression) o;
        return comparision == that.comparision &&
                Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comparision, left, right);
    }

    @Override
    public String toString() {
        return "ComparisionExpression{" +
                "comparision=" + comparision +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    private enum Comparision implements Predicate<Integer> {
        GT(x -> x > 0),
        EQ(x -> x == 0),
        LT(x -> x < 0),
        GE(GT.or(EQ)),
        LE(LT.or(EQ));

        private final Predicate<Integer> comparision;

        Comparision(Predicate<Integer> comparision) {
            this.comparision = comparision;
        }

        public boolean test(Integer integer) {
            return comparision.test(integer);
        }


        private static Comparision create(String s) {
            switch (s) {
                case ">" : return Comparision.GT;
                case ">=" : return Comparision.GE;
                case "=": return Comparision.EQ;
                case "<" : return Comparision.LT;
                case "=<": return Comparision.LE;
                default: throw new IllegalArgumentException("Unsupported comparision type: " + s);
            }
        }
    }
}
