package com.tobyclemson.lip.pattern2refactored;

public enum TokenType {
    NAME("NAME") {
        /* NAME: ('a'..'z'|'A'..'Z')+ ; */
        @Override public boolean isSatisfiedBy(char character) {
            return character >= 'a' && character <= 'z' ||
                    character >= 'A' && character <= 'Z';
        }
    },
    WHITESPACE("WHITESPACE") {
        /* WHITESPACE : (' '|'\t'|'\n'|'\r')* ;  */
        @Override public boolean isSatisfiedBy(char character) {
            return character == ' ' ||
                    character == '\t' ||
                    character == '\n' ||
                    character == '\r';
        }
    },
    COMMA("COMMA") {
        @Override public boolean isSatisfiedBy(char character) {
            return character == ',';
        }
    },
    LBRACK("LBRACK") {
        @Override public boolean isSatisfiedBy(char character) {
            return character == '[';
        }
    },
    RBRACK("RBRACK") {
        @Override public boolean isSatisfiedBy(char character) {
            return character == ']';
        }
    },
    EOF("<EOF>") {
        @Override public boolean isSatisfiedBy(char character) {
            return character == LookaheadBuffer.EOF_CHARACTER;
        }

        @Override public Token token(String text) {
            return super.token("<EOF>");
        }
    };

    private String name;

    TokenType(String name) {
        this.name = name;
    }

    public Token token(String text) {
        return new Token(this, text);
    }

    public Token token(char character) {
        return token(String.valueOf(character));
    }

    public abstract boolean isSatisfiedBy(char character);

    public String getName() {
        return name;
    }
}
