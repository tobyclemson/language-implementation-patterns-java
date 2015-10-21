package com.tobyclemson.lip.refactored.common;

import lombok.Value;

@Value
public class Token {
    public final TokenType type;
    public final String text;

    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    public String getTypeName() {
        return type.getName();
    }

    public boolean isOfType(TokenType type) {
        return this.type.equals(type);
    }
    
    @Override public String toString() {
        return "<'" + text + "', " + type.getName() + ">";
    }
}
