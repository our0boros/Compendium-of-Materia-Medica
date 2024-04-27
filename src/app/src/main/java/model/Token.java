package model;

import java.util.Objects;

/**
 * Token class to save extracted token from tokenizer.
 * Each token has its surface form saved in {@code token}
 * and type saved in {@code type} which is one of the predefined type in Type enum.
 * The following are the different types of tokens:
 *
 *  Search Grammar: #: {TAG1, TAG2, TAG3}, $: {TEXT1, TEXT2, TEXT3}, *: {&}
 *  if * search method is &-and: find TAG1: TEXT1 and TAG2: TEXT2 and ...
 *  if * search method is |-or: find TAG1: TEXT1 or TAG2: TEXT2 and ...
 *
 * TAG: #
 * TEXT: $
 * LBRACE: {
 * RBRACE: }
 * SEP: ,
 * COLON: :
 * OR: |
 * AND: &
 * STR: TEXT/ TAG/ ...
 */
public class Token {
    // The following enum defines different types of tokens. Example of accessing these: Token.Type.INT
    public enum Type {TAG, TEXT, LBRACE, RBRACE, SEP, COLON, OR, AND, STR}

    /**
     * The following exception should be thrown if a tokenizer attempts to tokenize something that is not of one
     * of the types of tokens.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    // Fields of the class Token.
    private final String token; // Token representation in String form.
    private final Type type;    // Type of the token.

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == Type.STR) {
            return "STR(" + token + ")";
        } else {
            return type + "";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true; // Same hashcode.
        if (!(other instanceof Token)) return false; // Null or not the same type.
        return this.type == ((Token) other).getType() && this.token.equals(((Token) other).getToken()); // Values are the same.
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type);
    }
}
