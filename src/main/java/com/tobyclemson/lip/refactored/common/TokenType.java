package com.tobyclemson.lip.refactored.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class TokenType {
    @Getter String name;

    public Token token(String text) {
        return new Token(this, text);
    }

    public Token token(char character) {
        return token(String.valueOf(character));
    }

    public abstract boolean isSatisfiedBy(Character character);
}
