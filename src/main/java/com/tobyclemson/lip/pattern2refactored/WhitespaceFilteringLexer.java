package com.tobyclemson.lip.pattern2refactored;

import static com.tobyclemson.lip.pattern2refactored.TokenType.WHITESPACE;

public class WhitespaceFilteringLexer implements Lexer {
    private final Lexer delegate;

    public WhitespaceFilteringLexer(Lexer delegate) {
        this.delegate = delegate;
    }

    @Override public Token nextToken() {
        Token token = delegate.nextToken();
        while (token.isOfType(WHITESPACE)) {
            token = delegate.nextToken();
        }
        return token;
    }
}
