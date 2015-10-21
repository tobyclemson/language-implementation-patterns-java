package com.tobyclemson.lip.refactored.pattern2.lexers;

import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.common.TokenType;
import com.tobyclemson.lip.refactored.pattern2.Lexer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilteringLexer implements Lexer {
    TokenType tokenType;
    Lexer delegate;

    @Override public Token nextToken() {
        Token token = delegate.nextToken();
        while (token.isOfType(tokenType)) {
            token = delegate.nextToken();
        }
        return token;
    }
}
