package com.example.compendiumofmateriamedica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import static model.Parser.Token.Type;

import org.junit.Test;

import java.util.ArrayList;

import model.Parser.Token;
import model.Parser.Tokenizer;

public class TokenizerTest {

    private static Tokenizer tokenizer;
    // =======================================================================================
    // Test All Token Basic: TAG, TEXT, LBRACE, RBRACE, SEP, COLON, METHOD, OR, AND, STR
    // =======================================================================================
    @Test(timeout=1000)
    public void testAllTokenBasic() {
        Type[] types = {Type.TAG, Type.TEXT, Type.LBRACE, Type.RBRACE, Type.SEP, Type.COLON, Type.METHOD, Type.OR, Type.AND};
        String[] testCases = {"#${|*", "$}{*", "{*&|", "}{}！", ",,|*&", ":{}:|&", "***{##", "|#$}{", "&##|*"};
        for (int idx = 0; idx < types.length; idx++) {
            tokenizer = new Tokenizer(testCases[idx]);
            // check the type of the first token
            assertEquals("wrong token type", types[idx], tokenizer.current().getType());
            // check the actual token value
            assertEquals("wrong test token", testCases[idx].substring(0, 1), tokenizer.current().getToken());
        }
    }

    @Test(timeout=1000)
    public void testMidTokenCase() {
        Type[] types = {Type.TEXT, Type.COLON, Type.LBRACE, Type.STR, Type.SEP,  Type.STR, Type.SEP, Type.STR, Type.SEP, Type.STR, Type.RBRACE,
            Type.SEP, Type.METHOD, Type.COLON, Type.LBRACE, Type.OR, Type.RBRACE};
        String plainText = "$: {1,1,227114,2024-01-23}, *: {|}";
        tokenizer = new Tokenizer(plainText, false);
//        String plainText = "#: {ID, COMMON_NAME, SLUG, GENUS, FAMILY}, $: {77116, Milfoil, dactylis-glomerata, Quercus, Asteraceae}, *: {|}";
        int count = 0;
        while (tokenizer.hasNext()) {
            // check the type of the first token
            assertEquals("wrong token type", types[count], tokenizer.current().getType());
            System.out.println(tokenizer.current().getType() + " " + tokenizer.current().getToken());
            tokenizer.next();
            count++;
        }
    }
}
