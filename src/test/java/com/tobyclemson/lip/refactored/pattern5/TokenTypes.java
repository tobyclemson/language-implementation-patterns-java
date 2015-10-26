package com.tobyclemson.lip.refactored.pattern5;

import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.common.TokenType;
import com.tobyclemson.lip.refactored.pattern2.Constants;

public class TokenTypes {
    public static final TokenType NAME = new TokenType("NAME") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character >= 'a' && character <= 'z' ||
                    character >= 'A' && character <= 'Z';
        }
    };
    public static final TokenType WHITESPACE = new TokenType("WHITESPACE") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == ' ' ||
                    character == '\t' ||
                    character == '\n' ||
                    character == '\r';
        }
    };
    public static final TokenType COMMA = new TokenType("COMMA") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == ',';
        }
    };
    public static final TokenType EQUALS = new TokenType("EQUALS") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == '=';
        }
    };
    public static final TokenType LBRACK = new TokenType("LBRACK") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == '[';
        }
    };
    public static final TokenType RBRACK = new TokenType("RBRACK") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == ']';
        }
    };
    public static final TokenType EOF = new TokenType("<EOF>") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == Constants.EOF_CHARACTER;
        }

        @Override public Token token(String text) {
            return super.token("<EOF>");
        }
    };
}
