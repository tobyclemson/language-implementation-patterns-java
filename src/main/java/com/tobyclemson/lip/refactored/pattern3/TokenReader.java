package com.tobyclemson.lip.refactored.pattern3;

import com.tobyclemson.lip.refactored.common.Reader;
import com.tobyclemson.lip.refactored.pattern2.Lexer;
import com.tobyclemson.lip.refactored.pattern2.Token;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenReader implements Reader<Token> {
    Lexer lexer;

    @Override public Token readNext() {
        return lexer.nextToken();
    }
}
