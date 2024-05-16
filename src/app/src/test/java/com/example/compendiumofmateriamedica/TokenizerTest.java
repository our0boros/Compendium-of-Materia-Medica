package com.example.compendiumofmateriamedica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import static model.Parser.Token.Type;

import org.junit.Test;

import java.util.ArrayList;

import model.Parser.Token;
import model.Parser.Tokenizer;

/**
 * @author: Hongjun Xu
 * @uid: u7733037
 * @datetime: 2024/05/16
 * @description: Tokenizer Test
 */
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
        Type[] types = {
                Type.TEXT, Type.COLON, Type.LBRACE,
                    Type.STR, Type.SEP, Type.STR, Type.SEP, Type.STR, Type.SEP, Type.STR,
                Type.RBRACE, Type.SEP,
                Type.METHOD, Type.COLON, Type.LBRACE,
                    Type.OR,
                Type.RBRACE};
        String plainText = "$: {1,1,227114,2024-01-23}, *: {|}";
        tokenizer = new Tokenizer(plainText, false);
        int count = 0;
        while (tokenizer.hasNext()) {
            // check the type of the first token
            assertEquals("wrong token type", types[count], tokenizer.current().getType());
            System.out.println(tokenizer.current().getType() + " " + tokenizer.current().getToken());
            tokenizer.next();
            count++;
        }
    }
    @Test(timeout=1000)
    public void testAdvancedTokenResult() {
        Type[] types = {
                Type.TAG, Type.COLON, Type.LBRACE,
                    Type.STR, Type.SEP, Type.STR, Type.SEP, Type.STR, Type.SEP, Type.STR, Type.SEP, Type.STR,
                Type.RBRACE, Type.SEP,
                Type.TEXT, Type.COLON, Type.LBRACE,
                    Type.STR, Type.SEP, Type.STR, Type.SEP, Type.STR, Type.SEP, Type.STR, Type.SEP, Type.STR,
                Type.RBRACE, Type.SEP,
                Type.METHOD, Type.COLON, Type.LBRACE,
                    Type.OR,
                Type.RBRACE};
        String plainText = "#: {ID, COMMON_NAME, SLUG, GENUS, FAMILY}, $: {77116, Milfoil, dactylis-glomerata, Quercus, Asteraceae}, *: {|}";
        tokenizer = new Tokenizer(plainText, false);

        int count = 0;
        while (tokenizer.hasNext()) {
            // check the type of the first token
            assertEquals("wrong token type", types[count], tokenizer.current().getType());
            System.out.println(tokenizer.current().getType() + " " + tokenizer.current().getToken());
            tokenizer.next();
            count++;
        }
    }

    @Test(timeout=1000)
    public void testExceptionToken() {
        // Test a series of non-identifiable tokens
        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("）（*@#……", false);
            tokenizer.next();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("燙燙燙錕斤拷~", false);
            tokenizer.next();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("痛みを感じる", false);
            tokenizer.next();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("Сайн уу", false);
            tokenizer.next();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("(╬▔皿▔)╯)", false);
            tokenizer.next();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("(👉ﾟヮﾟ)👉)", false);
            tokenizer.next();
        });
    }
}
