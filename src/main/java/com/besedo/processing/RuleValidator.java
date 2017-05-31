package com.besedo.processing;

import com.besedo.processing.el.Context;
import com.besedo.processing.el.expressions.ThenExpression;
import com.besedo.processing.json.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public class RuleValidator implements Consumer<Document> {

    private final List<ThenExpression<String>> rules;
    private final List<BiConsumer<Document, String>> statisticConsumers;

    public RuleValidator(List<ThenExpression<String>> rules) {
        this.rules = rules;
        this.statisticConsumers = new ArrayList<>();
    }

    public boolean addStatisticConsumer(BiConsumer<Document, String> documentStringBiConsumer) {
        return statisticConsumers.add(documentStringBiConsumer);
    }

    @Override
    public void accept(Document document) {
        Context context = new DocumentContext(document);
        String result = rules.stream()
                .map(then -> then.evaluate(context))
                .filter(Predicate.isEqual(Optional.empty()).negate())
                .findFirst()
                .flatMap(Function.identity())
                .orElseThrow(() -> new IllegalStateException("No rules applied for a document with id: " + document.getId()));
        notifyConsumers(document, result);
    }

    private void notifyConsumers(Document document, String flag) {
        statisticConsumers.forEach(consumer -> consumer.accept(document, flag));
    }
}
