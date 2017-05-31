package com.besedo.processing;


import com.besedo.processing.el.RuleParser;
import com.besedo.processing.el.expressions.ThenExpression;
import com.besedo.processing.json.DocumentParser;
import com.besedo.processing.json.StatisticWriter;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        String[] rules = {
                "$price > 20 AND $title CONTAINS (apple, banana) THEN red",
                "$price = 0 THEN red",
                "$description CONTAINS (apple, banana, strawberry) THEN red",
                "$price >= 10000 AND $description NOTCONTAINS (bmw) THEN yellow"
        };
        List<ThenExpression<String>> compiledRules = Arrays.stream(rules)
                .map(RuleParser::parseRule)
                .collect(Collectors.toList());
        compiledRules.add(RuleParser.createThenGreen());

        RuleValidator ruleValidator = new RuleValidator(compiledRules);
        DocumentParser documentParser = new DocumentParser();
        try (StatisticWriter statisticWriter = new StatisticWriter(new FileWriter("output.json"));
             Reader dataSource = Files.newBufferedReader(Paths.get(Main.class.getResource("/documents.json").toURI()))
        ) {
            documentParser.addDocumentConsumer(ruleValidator);
            ruleValidator.addStatisticConsumer(statisticWriter);
            documentParser.parse(dataSource);
        }
    }
}
