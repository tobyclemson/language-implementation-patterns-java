package com.tobyclemson.lip.refactored.pattern2;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import static com.tobyclemson.lip.refactored.pattern2.TokenType.WHITESPACE;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WhitespaceFilteringLexer implements Lexer {
    Lexer delegate;

    @Override public Token nextToken() {
        Token token = delegate.nextToken();
        while (token.isOfType(WHITESPACE)) {
            token = delegate.nextToken();
        }
        return token;
    }
}
