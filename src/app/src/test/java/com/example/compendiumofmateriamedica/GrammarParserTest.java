package com.example.compendiumofmateriamedica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Parser.SearchGrammarParser;
import model.Parser.Token;
import model.Parser.Tokenizer;

public class GrammarParserTest {
    private static Tokenizer tokenizer;


    @Test(timeout=1000)
    public void testSimpleConvert() throws IllegalAccessException {
        String[] testExample = new String[]{
                "#: {A, B, C}, $: {1, 2, 3}, *: {|}",
                "#: {aAa}, $: {bBb}, *: {|}",
                "#: {你好}, $: {世界}, *: {&}",
                "#: {Eihei}, $: {？}, *: {&}",
                "$: {1, 2, 3}, #: {A, B, C}, *: {|}",
                "$: {bBb}, #: {aAa}, *: {&}",
                "$: {世界}, #: {你好}, *: {|}"
        };
        Token.Type[] types = {Token.Type.OR, Token.Type.OR, Token.Type.AND, Token.Type.AND, Token.Type.OR, Token.Type.AND, Token.Type.OR};

        ArrayList<Map<String, ArrayList<String>>> expectResults = new ArrayList<>();
        expectResults.add(new ImmutableMap.Builder<String, ArrayList<String>>()
                .put("A".toUpperCase(), new ArrayList<>(List.of("1")))
                .put("B".toUpperCase(), new ArrayList<>(List.of("2")))
                .put("C".toUpperCase(), new ArrayList<>(List.of("3")))
                .build());
        expectResults.add(new ImmutableMap.Builder<String, ArrayList<String>>()
                .put("aAa".toUpperCase(), new ArrayList<>(List.of("bBb")))
                .build());
        expectResults.add(new ImmutableMap.Builder<String, ArrayList<String>>()
                .put("你好".toUpperCase(), new ArrayList<>(List.of("世界")))
                .build());
        expectResults.add(new ImmutableMap.Builder<String, ArrayList<String>>()
                .put("Eihei".toUpperCase(), new ArrayList<>(List.of("？")))
                .build());
        expectResults.add(new ImmutableMap.Builder<String, ArrayList<String>>()
                .put("A".toUpperCase(), new ArrayList<>(List.of("1")))
                .put("B".toUpperCase(), new ArrayList<>(List.of("2")))
                .put("C".toUpperCase(), new ArrayList<>(List.of("3")))
                .build());
        expectResults.add(new ImmutableMap.Builder<String, ArrayList<String>>()
                .put("aAa".toUpperCase(), new ArrayList<>(List.of("bBb")))
                .build());
        expectResults.add(new ImmutableMap.Builder<String, ArrayList<String>>()
                .put("你好".toUpperCase(), new ArrayList<>(List.of("世界")))
                .build());


        for (int idx = 0; idx < testExample.length; idx++) {
            System.out.println(testExample[idx]);
            tokenizer = new Tokenizer(testExample[idx], false);
            SearchGrammarParser searchGrammarParser = new SearchGrammarParser(tokenizer, false);
            Map<String, ArrayList<String>> results = searchGrammarParser.parseExp();
            System.out.println( new TreeMap<>(results));
            System.out.println(new TreeMap<>(expectResults.get(idx)));
            assertEquals(results, expectResults.get(idx));
            assertEquals(types[idx], searchGrammarParser.getSearchMethod());
        }
    }


    @Test(timeout=1000)
    public void testAdvanceConvert() throws IllegalAccessException {
        String testExample = "#: {ID, COMMON_NAME, SLUG, GENUS, FAMILY}, $: {77116, Milfoil, dactylis-glomerata, Quercus, Asteraceae}, *: {|}";

        Map<String, ArrayList<String>> expectResults = new ImmutableMap.Builder<String, ArrayList<String>>()
                .put("ID".toUpperCase(), new ArrayList<>(List.of("77116")))
                .put("COMMON_NAME".toUpperCase(), new ArrayList<>(List.of("Milfoil")))
                .put("SLUG".toUpperCase(), new ArrayList<>(List.of("dactylis-glomerata")))
                .put("GENUS".toUpperCase(), new ArrayList<>(List.of("Quercus")))
                .put("FAMILY".toUpperCase(), new ArrayList<>(List.of("Asteraceae")))
                .build();

        tokenizer = new Tokenizer(testExample, false);
        SearchGrammarParser searchGrammarParser = new SearchGrammarParser(tokenizer, false);
        Map<String, ArrayList<String>> results = searchGrammarParser.parseExp();
        assertEquals(results, expectResults);

    }

    @Test(timeout=1000)
    public void testIllegalProductionException() {
        String[] inputs = new String[] {
                "aha", "*: {|}", "*: {|}, #: {ID, COMMON_NAME, SLUG, GENUS, FAMILY}, $: {77116, Milfoil, dactylis-glomerata, Quercus, Asteraceae}",
                ",,,", "#: {Eihei}, #: {Eihei}, $: {？}, *: {&}", "#: {Eihei}, $: {？}, *: {:}", "#: {Eihei}, $: {{？}, *: {&}", "#: {Eihei}, $: {？}, *: {&}}",
                "#: {Eihei}, $$: {？}, *: {&}", "#:& {Eihei}, $: {？}, *: {&}"
        };

        for (String input : inputs) {
            // Provide a series of tokens that should invoke this exception
            assertThrows(SearchGrammarParser.IllegalProductionException.class, () -> {
                tokenizer = new Tokenizer(input, false);
                Map<String, ArrayList<String>> result = new SearchGrammarParser(tokenizer, false).parseExp();
            });
        }
    }
}
