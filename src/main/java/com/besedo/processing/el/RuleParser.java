package com.besedo.processing.el;

import com.besedo.processing.el.expressions.*;
import com.besedo.processing.el.expressions.BooleanAlgebraExpression;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RuleParser {

    private static final Pattern thenPattern = Pattern.compile("(.+) THEN (.+)$");
    private static final Pattern bracesPattern = Pattern.compile("\\((.+?)\\)");
    private static final Pattern delimiter = Pattern.compile("[\\s,]+");
    private static final Set<String> booleanOperators = new HashSet<>(Arrays.asList("AND"));
    private static final Set<String> comparisionOperators = new HashSet<>(Arrays.asList(">", ">=", "=", "<", "=<"));
    private static final Set<String> containsOperators = new HashSet<>(Arrays.asList("CONTAINS", "NOTCONTAINS"));

    /**
     * Parse passed rule and returns expression. Here we assume that top most expression is ThenExpression and also we
     * assume that string contains only single then expression.
     * @param rule string representation
     * @return expression
     */
    public static ThenExpression<String> parseRule(String rule) {
        Matcher matcher = thenPattern.matcher(rule);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Rule must contains THEN word: " + rule);
        }
        return new ThenExpression<>(parseBooleanExpression(matcher.group(1)), new ConstantExpression<>(matcher.group(2)));
    }

    private static Expression<Boolean> parseBooleanExpression(String condition) {
        Scanner scanner = new Scanner(condition);
        scanner.useDelimiter(delimiter);
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Can't find expression in string: " + condition);
        }
        return parseBooleanExpression(null, scanner);
    }

    private static Expression<Boolean> parseBooleanExpression(Expression<Boolean> left, Scanner condition) {
        String l = condition.next();
        if (booleanOperators.contains(l) || left != null) {
            if (left == null || !booleanOperators.contains(l)) {
                throw new IllegalArgumentException("Can't compile expression. Wrong boolean expression: " + l);
            }
            return new BooleanAlgebraExpression(l, left, parseBooleanExpression(null, condition));
        }
        try {
            String operator = condition.next();
            if (comparisionOperators.contains(operator)) {
                String r = condition.next();
                ComparisionExpression result = new ComparisionExpression(operator, createSimpleExpression(l), createSimpleExpression(r));
                return condition.hasNext() ? parseBooleanExpression(result, condition) : result;
            }
            if (!containsOperators.contains(operator)) {
                throw new IllegalArgumentException("Can't compile expression. Unknown operator: " + operator);
            }
            String right = condition.findInLine(bracesPattern);
            if (right == null) {
                throw new IllegalArgumentException("Can't compile expression. (Not?)Contains list must starts with (");
            }
            right = right.substring(1, right.length() - 1);
            List<String> contains = Arrays.asList(delimiter.split(right));
            ContainsExpression result = new ContainsExpression("CONTAINS".equals(operator), createStringExpression(l), contains);
            return condition.hasNext() ? parseBooleanExpression(result, condition) : result;
        } catch (NoSuchElementException nse) {
            throw new IllegalArgumentException("Can't compile expression", nse);
        }
    }

    private static Expression<String> createStringExpression(String source) {
        if (source.startsWith("$")) {
            if (source.length() <= 1) {
                throw new IllegalArgumentException("Variable name's length must be at least 1 character");
            }
            return new VariableBindingExpression(source.substring(1));
        }
        if (comparisionOperators.contains(source) || containsOperators.contains(source)) {
            throw new IllegalArgumentException("Can't compile expression: " + source);
        }
        return new ConstantExpression<>(source);
    }

    private static Expression<Comparable> createSimpleExpression(String source) {
        if (source.startsWith("$")) {
            if (source.length() <= 1) {
                throw new IllegalArgumentException("Variable name's length must be at least 1 character");
            }
            return new VariableBindingExpression(source.substring(1));
        }
        if (comparisionOperators.contains(source) || containsOperators.contains(source)) {
            throw new IllegalArgumentException("Can't compile expression: " + source);
        }
        try {
            return new ConstantExpression<>(new BigDecimal(source));
        } catch (NumberFormatException nfe) {
            return new ConstantExpression<>(source);
        }
    }

    public static ThenExpression<String> createThenGreen() {
        return new ThenExpression<>(new ConstantExpression<>(true), new ConstantExpression<>("green"));
    }
}
