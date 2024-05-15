package com.example.compendiumofmateriamedica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import model.Parser.Token;
import model.Parser.Tokenizer;

public class TokenizerTest {

    private static Tokenizer tokenizer;

    @Test(timeout=1000)
    public void testAddToken() {
        tokenizer = new Tokenizer("*{|&}");

        // check the type of the first token
        assertEquals("wrong token type", Token.Type.METHOD, tokenizer.current().getType());

        // check the actual token value"
        assertEquals("wrong token token", "*", tokenizer.current().getToken());
    }

}
