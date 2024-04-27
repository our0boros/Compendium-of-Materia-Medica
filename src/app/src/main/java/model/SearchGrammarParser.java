package model;

import android.util.Log;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * parser grammar:
 * <Exp>        := <TagColumn>, <TextColumn>, <METHOD> | <TextColumn>, <TagColumn>, <METHOD>
 * <TagColumn>  := #: { <Content> },
 * <TextColumn> := $: { <Content> },
 * <Method>     :=  *:{&} |  *:{|}
 * <Content>    := STR | STR, <Content>
 */
public class SearchGrammarParser {

    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    Tokenizer tokenizer;
    // false as &, true as |
    Boolean searchMethod = null;
    Map<String, String> output;

    public SearchGrammarParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Map<String, String> parseExp() throws IllegalAccessException {

        Log.println(Log.ASSERT, "DEBUG", "[parseExp] start parsing token");
        Stack<String> BRACEStack = new Stack<>();
        ArrayList<String> columns = new ArrayList<>();
        columns.add(""); // set init String
        while (tokenizer.hasNext()) {
            // store BRACE, only check SEP out of BRACE
            if (tokenizer.current().getType() == Token.Type.LBRACE) BRACEStack.push("{");
            if (tokenizer.current().getType() == Token.Type.RBRACE) {
                try {
                    BRACEStack.pop();
                } catch (EmptyStackException e) {
                    throw new IllegalProductionException("LBRACE and RBRACE do not close");
                }
            }
            String current = columns.get(columns.size() - 1);
            columns.set(columns.size() - 1, current + tokenizer.current().getToken() + " ");
            // Only count Symbol outside BRA
            if (BRACEStack.size() == 0 &&
                    tokenizer.current().getType() == Token.Type.SEP) {
                columns.add("");
            }

            tokenizer.next();
        }
        Log.println(Log.ASSERT, "DEBUG", "[parseExp] found mapping colums: " + columns);
        if (columns.size() != 3) throw new IllegalProductionException("Mapping columns should be 3/ Tag, Text, Method");
        ArrayList<String> tagList;
        ArrayList<String> textList;
        // Case: <TagColumn>, <TextColumn>, <METHOD>
        if (columns.get(0).charAt(0) == '#' && columns.get(1).charAt(0) == '$') {
            SearchGrammarParser searchGrammarParser1 = new SearchGrammarParser(new Tokenizer(columns.get(0)));
            SearchGrammarParser searchGrammarParser2 = new SearchGrammarParser(new Tokenizer(columns.get(1)));
            tagList = searchGrammarParser1.parseTagColumn();
            textList = searchGrammarParser2.parseTextColumn();
        // Case: <TextColumn>, <TagColumn>, <METHOD>
        } else if (columns.get(0).charAt(0) == '$' && columns.get(1).charAt(0) == '#') {
            SearchGrammarParser searchGrammarParser1 = new SearchGrammarParser(new Tokenizer(columns.get(1)));
            SearchGrammarParser searchGrammarParser2 = new SearchGrammarParser(new Tokenizer(columns.get(0)));
            tagList = searchGrammarParser1.parseTagColumn();
            textList = searchGrammarParser2.parseTextColumn();
        } else throw new IllegalAccessException("Unexpected Columns");
        // check search method
        SearchGrammarParser searchGrammarParser3 = new SearchGrammarParser(new Tokenizer(columns.get(2)));
        this.searchMethod = searchGrammarParser3.parseMethod();
        if (this.searchMethod == null) throw new IllegalProductionException("Invalid search Method");
        // if tag do not match text
        if (tagList.size() != textList.size()) throw new IllegalProductionException("Unmatched entries size");
        // store output
        output = new HashMap<>();
        for (int idx = 0; idx < tagList.size(); idx++) {
            output.put(tagList.get(idx), textList.get(idx));
        }

        return output;
    }

    // <TagColumn>  := #: { <Content> },
    public ArrayList<String> parseTagColumn() {
        Token current = null;
        StringBuilder toNext = new StringBuilder();
        int tokenCount = 0;
        // Check grammar sequence
        while (tokenizer.hasNext()) {
            current = tokenizer.current();
            if (tokenCount == 0 && current.getType() != Token.Type.TAG) throw new IllegalProductionException("Unexpected token: " + current.getToken());
            else if (tokenCount == 1 && current.getType() != Token.Type.COLON) throw new IllegalProductionException("Unexpected token: " + current.getToken());
            else if (tokenCount == 2 && current.getType() != Token.Type.LBRACE) throw new IllegalProductionException("Unexpected token: " + current.getToken());
            else toNext.append(current.getToken()).append(" ");
            tokenCount++;
            tokenizer.next();
        }
        if (current == null || current.getType() != Token.Type.RBRACE) throw new IllegalProductionException("Unexpected token: " + current.getToken());
        // delete last "}"
        toNext.substring(toNext.length() - 2, toNext.length() - 1);
        // get <Content>
        SearchGrammarParser searchGrammarParser = new SearchGrammarParser(new Tokenizer(toNext.toString()));
        return searchGrammarParser.parseContent();
    }
    // <TextColumn>  := $: { <Content> },
    public ArrayList<String> parseTextColumn() {
        Token current = null;
        StringBuilder toNext = new StringBuilder();
        int tokenCount = 0;
        // Check grammar sequence
        while (tokenizer.hasNext()) {
            current = tokenizer.current();
            if (tokenCount == 0 && current.getType() != Token.Type.TEXT) throw new IllegalProductionException("Unexpected token: " + current.getToken());
            else if (tokenCount == 1 && current.getType() != Token.Type.COLON) throw new IllegalProductionException("Unexpected token: " + current.getToken());
            else if (tokenCount == 2 && current.getType() != Token.Type.LBRACE) throw new IllegalProductionException("Unexpected token: " + current.getToken());
            else toNext.append(current.getToken()).append(" ");
            tokenCount++;
            tokenizer.next();
        }
        if (current == null || current.getType() != Token.Type.RBRACE) throw new IllegalProductionException("Unexpected token: " + current.getToken());
        // delete last "}"
        toNext.substring(toNext.length() - 2, toNext.length() - 1);
        // get <Content>
        SearchGrammarParser searchGrammarParser = new SearchGrammarParser(new Tokenizer(toNext.toString()));
        return searchGrammarParser.parseContent();
    }
    // <Content>    := STR | STR, <Content>
    public ArrayList<String> parseContent() {
        ArrayList<String> output = new ArrayList<>();

        StringBuilder toNext = new StringBuilder();
        int tokenCount = 0;
        while (tokenizer.hasNext()) {
            toNext.append(tokenizer.current().getToken()).append(" ");
            tokenCount++;
            tokenizer.next();
        }
        return null;
    }
    public Boolean parseMethod() {
        return null;
    }
}
