package com.example.compendiumofmateriamedica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import model.Parser.SearchGrammarParser;
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

        ArrayList<Map<String, String>> expectResults = new ArrayList<>();
        expectResults.add(new ImmutableMap.Builder<String, String>()
                .put("A".toUpperCase(), "1").put("B".toUpperCase(), "2").put("C".toUpperCase(), "3").build());
        expectResults.add(new ImmutableMap.Builder<String, String>()
                .put("aAa".toUpperCase(), "bBb").build());
        expectResults.add(new ImmutableMap.Builder<String, String>()
                .put("你好".toUpperCase(), "世界").build());
        expectResults.add(new ImmutableMap.Builder<String, String>()
                .put("Eihei".toUpperCase(), "？").build());
        expectResults.add(new ImmutableMap.Builder<String, String>()
                .put("A".toUpperCase(), "1").put("B".toUpperCase(), "2").put("C".toUpperCase(), "3").build());
        expectResults.add(new ImmutableMap.Builder<String, String>()
                .put("aAa".toUpperCase(), "bBb").build());
        expectResults.add(new ImmutableMap.Builder<String, String>()
                .put("你好".toUpperCase(), "世界").build());


        for (int idx = 0; idx < testExample.length; idx++) {
            System.out.println(testExample[idx]);
            tokenizer = new Tokenizer(testExample[idx], false);
            SearchGrammarParser searchGrammarParser = new SearchGrammarParser(tokenizer, false);
            Map<String, String> results = searchGrammarParser.parseExp();
            System.out.println( new TreeMap<>(results));
            System.out.println(new TreeMap<>(expectResults.get(idx)));
            assertEquals(results, expectResults.get(idx));
        }
    }
}
