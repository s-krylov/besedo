# Task description

We have list of documents (100.000 entries), each of document has structure described below. We
need to execute set of rules (4 rules, listed in array) against each document in order to make a
decision about the document. As a result we need to get JSON file with following structure:
{documentId, flag}, where each document has decision based on rules execution.

## Document entity
- id (int)
- title (string, max 255 chars)
- price (float 5,0)
- description (text)

## Rules
String[] rules = {
"$price > 20 AND $title CONTAINS (apple, banana) THEN red",
"$price = 0 THEN red",
"$description CONTAINS (apple, banana, strawberry) THEN red",
"$price >= 10000 AND $description NOTCONTAINS (bmw) THEN yellow"
};

## Rules legend and agreements
- string which starts with $ means a field in entity (one can assume $ sign will not be presented in
any other context)
- rules executes agains documents, if rule applies then document needs to market with the flag,
mentioned in the rule
- lists for CONTAINS and NOTCONTAINS are always started with “(“ and finished with “)”, one
can assume () will not be presented in any other context
- AND, CONTAINS, NOTCONTAINS, THEN are reserved words, one can assume they will not be
presented in any other context
- if no rule can not be applied to a document, then flag the document as “green”
- CONTAINS have an array of words, it is enough to meet one word in the field to have
CONTAINS to be true

## Flow
1. Write a script which runs each rule against each document
2. Save the result of rules check into new JSON file
3. Send us code and the JSON file with results

Use Java 8. Code needs to be covered with unit-tests.