package model.Parser;

import android.util.Log;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author: Hongjun Xu
 * @datetime: 2024/05/16
 * @description:
 * Read the plain text of the input and convert it into a machine-friendly concrete variable class
 * String -> Map< String, String >
 * parser grammar:
 * <Exp>        := <TagColumn>, <TextColumn>, <METHOD> | <TextColumn>, <TagColumn>, <METHOD>
 * <TagColumn>  := #: { <Content> },
 * <TextColumn> := $: { <Content> },
 * <Method>     :=  *:{&} |  *:{|}
 * <Content>    := STR | STR, <Content>
 */
public class SearchGrammarParser {
    // Exceptions
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    Tokenizer tokenizer;
    // Only accept AND, OR
    Token.Type searchMethod = null;
    Map<String, String> output;

    public SearchGrammarParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Parse tokens into expression
     * <Exp>        := <TagColumn>, <TextColumn>, <METHOD> | <TextColumn>, <TagColumn>, <METHOD>
     * @return
     * @throws IllegalAccessException
     */
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
        Log.println(Log.ASSERT, "DEBUG", "[parseExp] found mapping columns: " + columns);
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
        this.searchMethod = searchGrammarParser3.parseMethod() ? Token.Type.OR : Token.Type.AND;
        if (this.searchMethod == null) throw new IllegalProductionException("Invalid search Method");
        // if tag do not match text
        if (tagList.size() != textList.size()) throw new IllegalProductionException("Unmatched entries size");
        // store output
        output = new HashMap<>();
        for (int idx = 0; idx < tagList.size(); idx++) {
            output.put(tagList.get(idx).toUpperCase(), textList.get(idx));
        }

        return output;
    }

    /**
     * Parse Tag column into Content
     * <TagColumn>  := #: { <Content> },
     * @return Tag list
     */
    public ArrayList<String> parseTagColumn() {
        ArrayList<Token> fullToken = tokenizer.getFullToken();
        if (fullToken.get(0).getType() == Token.Type.TAG &&
                fullToken.get(1).getType() == Token.Type.COLON &&
                fullToken.get(2).getType() == Token.Type.LBRACE &&
                fullToken.get(fullToken.size() - 1).getType() == Token.Type.SEP &&
                fullToken.get(fullToken.size() - 2).getType() == Token.Type.RBRACE) {
            StringBuilder recursiveContent = new StringBuilder();
            for (int idx = 3; idx < fullToken.size() - 2; idx++) {
                recursiveContent.append(fullToken.get(idx).getToken()).append(" ");
            }
            SearchGrammarParser searchGrammarParser = new SearchGrammarParser(new Tokenizer(recursiveContent.toString()));
            return searchGrammarParser.parseContent();
        } else {
            throw new IllegalProductionException("Unexpected token...");
        }
    }

    /**
     * Parse Text column into Content
     * <TextColumn>  := $: { <Content> },
     * @return Text list
     */
    public ArrayList<String> parseTextColumn() {
        ArrayList<Token> fullToken = tokenizer.getFullToken();
        if (fullToken.get(0).getType() == Token.Type.TEXT &&
                fullToken.get(1).getType() == Token.Type.COLON &&
                fullToken.get(2).getType() == Token.Type.LBRACE &&
                fullToken.get(fullToken.size() - 1).getType() == Token.Type.SEP &&
                fullToken.get(fullToken.size() - 2).getType() == Token.Type.RBRACE) {
            StringBuilder recursiveContent = new StringBuilder();
            for (int idx = 3; idx < fullToken.size() - 2; idx++) {
                recursiveContent.append(fullToken.get(idx).getToken()).append(" ");
            }
            SearchGrammarParser searchGrammarParser = new SearchGrammarParser(new Tokenizer(recursiveContent.toString()));
            return searchGrammarParser.parseContent();
        } else {
            throw new IllegalProductionException("Unexpected token...");
        }
    }

    /**
     * Parse Content into Real Token or next Content
     * <Content>    := STR | STR, <Content>
     * @return Token List
     */
    public ArrayList<String> parseContent() {
        ArrayList<Token> fullToken = tokenizer.getFullToken();
        // Case: STR
        if (fullToken.size() == 1) {
            // if [STR]
            if (fullToken.get(0).getType() == Token.Type.STR) {
                ArrayList<String> output = new ArrayList<>();
                output.add(fullToken.get(0).getToken());
                return output;
            } else {
                throw new IllegalProductionException("Unexpected token: " + fullToken.get(0));
            }
            // Case: STR, <Content>
        } else {
            // if [STR ,]
            if (fullToken.get(0).getType() == Token.Type.STR &&
                    fullToken.get(1).getType() == Token.Type.SEP) {
                StringBuilder recursiveContent = new StringBuilder();
                for (int idx = 2; idx < fullToken.size(); idx++) {
                    recursiveContent.append(fullToken.get(idx).getToken()).append(" ");
                }
                SearchGrammarParser searchGrammarParser = new SearchGrammarParser(new Tokenizer(recursiveContent.toString()));
                ArrayList<String> output = searchGrammarParser.parseContent();
                output.add(0, fullToken.get(0).getToken());
                return output;
            } else {
                throw new IllegalProductionException("Unexpected token: " + fullToken.get(0) + " - " + fullToken.get(1));
            }
        }
    }
    // *:{&} |  *:{|}
    public Boolean parseMethod() {
        ArrayList<Token> fullToken = tokenizer.getFullToken();
        if (fullToken.size() != 5) throw new IllegalProductionException("Invalid Method Form");
        if (fullToken.get(0).getType() != Token.Type.METHOD) throw new IllegalProductionException("Unexpected Token: " + fullToken.get(0));
        if (fullToken.get(1).getType() != Token.Type.COLON) throw new IllegalProductionException("Unexpected Token: " + fullToken.get(1));
        if (fullToken.get(2).getType() != Token.Type.LBRACE) throw new IllegalProductionException("Unexpected Token: " + fullToken.get(2));
        if (!(fullToken.get(3).getType() == Token.Type.AND || fullToken.get(3).getType() == Token.Type.OR)) throw new IllegalProductionException("Unexpected Token: " + fullToken.get(3));
        if (fullToken.get(4).getType() != Token.Type.RBRACE) throw new IllegalProductionException("Unexpected Token: " + fullToken.get(4));
        // AND false, OR true
        return fullToken.get(3).getType() == Token.Type.OR;
    }

    public Token.Type getSearchMethod() {
        return searchMethod;
    }
}
