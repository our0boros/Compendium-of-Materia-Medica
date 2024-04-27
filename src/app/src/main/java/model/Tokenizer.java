package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Tokenizer {
    private String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;     // The current token. The next token is extracted when next() is called.
    private ArrayList<Token> fullToken;

    public Tokenizer(String text) {
        buffer = text;
        next();
    }

    // define Type.STR
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[_a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }

    public void next() {
        buffer = buffer.trim();

        if (buffer.isEmpty()) {
            currentToken = null;
            return;
        }

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

        else if (isLetterDigitOrChinese(String.valueOf(firstChar))) {
            int count = 0;
            do {
                count++;
            } while (count < buffer.length() && isLetterDigitOrChinese(String.valueOf(buffer.charAt(count))));
            currentToken = new Token(buffer.substring(0, count), Token.Type.STR);
        }
        else throw new Token.IllegalTokenException("Unexpected token");

        // Remove the extracted token from buffer
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Returns the current token extracted by {@code next()}
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Check whether tokenizer still has tokens left
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }

    public ArrayList<Token> getFullToken() {
        this.fullToken = new ArrayList<>();
        while (this.hasNext()) {
            this.fullToken.add(this.currentToken);
            this.next();
        }
        return this.fullToken;
    }
}
