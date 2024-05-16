package model.Parser;

import com.example.compendiumofmateriamedica.GeneralFunctions;
import com.example.compendiumofmateriamedica.MyApp;

import java.util.ArrayList;

/**
 * @author: Hongjun Xu
 * @datetime: 2024/05/16
 * @description: Convert the input PlainText into tokens
 */
public class Tokenizer {
    // ====================================
    // Class Tokenizer Fields
    // ====================================
    private String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;     // The current token. The next token is extracted when next() is called.

    public Tokenizer(String text) {
        buffer = text;
        next();
    }
    // ====================================
    // METHODS
    // ====================================

    /**
     * define Type.STR
     * Use regular expressions to determine whether the current
     * string meets the requirements of STR. Currently, STR includes
     * any English, numbers, Chinese and common symbols except
     * grammatical symbols.
     * @param str
     * @return is STR
     */
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[_a-z0-9A-Z\u4e00-\u9fa5-.* ！？“”]+$";
        return str.matches(regex);
    }

    /**
     * Streaming reads the tokenizable object of the current plain
     * text and returns the read token, while deleting the
     * corresponding part of the plain text.
     */
    public void next() {
        // remove space
        buffer = buffer.trim();
        // exception case
        if (buffer.isEmpty()) {
            currentToken = null;
            return;
        }
        // check the first character in stream
        char firstChar = buffer.charAt(0);
        if (firstChar == '#') currentToken = new Token("#", Token.Type.TAG);
        else if (firstChar == '$') currentToken = new Token("$", Token.Type.TEXT);
        else if (firstChar == '{') currentToken = new Token("{", Token.Type.LBRACE);
        else if (firstChar == '}') currentToken = new Token("}", Token.Type.RBRACE);
        else if (firstChar == ',') currentToken = new Token(",", Token.Type.SEP);
        else if (firstChar == ':') currentToken = new Token(":", Token.Type.COLON);
        else if (firstChar == '*') currentToken = new Token("*", Token.Type.METHOD);
        else if (firstChar == '|') currentToken = new Token("|", Token.Type.OR);
        else if (firstChar == '&') currentToken = new Token("&", Token.Type.AND);
        // When processing STR, since it may contain more than a single character,
        // you need to loop through until you find characters that do not meet the
        // requirements of STR (, } ...)
        else if (isLetterDigitOrChinese(String.valueOf(firstChar))) {
            int count = 0;
            // Iterative find all mapping chars
            do {
                count++;
            } while (count < buffer.length() && isLetterDigitOrChinese(String.valueOf(buffer.charAt(count))));
            // ***********************************************************
            // One of the special effects of Tokenizer can effectively block related sensitive words
            // ***********************************************************
            String newToken = buffer.substring(0, count);
            if (GeneralFunctions.getInstance().isSensitiveWord(newToken)) {
                // When sensitive words appear, use * instead
                newToken = "*".repeat(newToken.length());
            }
            currentToken = new Token(newToken, Token.Type.STR);
        }
        else throw new Token.IllegalTokenException("Unexpected token:" + firstChar);

        // Remove the extracted token from buffer
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Function From CLab-06
     * Returns the current token extracted by {@code next()}
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Function From CLab-06
     * Check whether tokenizer still has tokens left
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }

    /**
     * In order to facilitate Parser's quick processing (the original
     * hasNext method is logically misleading and complicates the
     * problem), we directly implement the quick list of the entire
     * tokenizer
     * @return Token List
     */
    public ArrayList<Token> getFullToken() {
        ArrayList<Token> fullToken = new ArrayList<>();
        // Iterate all plain text
        while (this.hasNext()) {
            fullToken.add(this.currentToken);
            this.next();
        }
        return fullToken;
    }
}
