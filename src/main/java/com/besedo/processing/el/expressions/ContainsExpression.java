package com.besedo.processing.el.expressions;

import com.besedo.processing.el.Context;
import com.besedo.processing.el.Expression;

import java.util.List;
import java.util.Objects;


public class ContainsExpression implements Expression<Boolean> {

    private final boolean contains;
    private final Expression<String> left;
    private final List<String> words;

    public ContainsExpression(boolean contains, Expression<String> left, List<String> words) {
        this.contains = contains;
        this.left = left;
        this.words = words;
    }

    @Override
    public Boolean evaluate(Context context) {
        String leftRes = left.evaluate(context);
        return contains == words.stream().anyMatch(leftRes::contains);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContainsExpression that = (ContainsExpression) o;
        return contains == that.contains &&
                Objects.equals(left, that.left) &&
                Objects.equals(words, that.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contains, left, words);
    }

    @Override
    public String toString() {
        return "ContainsExpression{" +
                "contains=" + contains +
                ", left=" + left +
                ", words=" + words +
                '}';
    }
}
