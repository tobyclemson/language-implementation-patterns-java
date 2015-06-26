package com.tobyclemson.lip.refactored.pattern2;

public enum TokenType {
    NAME("NAME") {
        /* NAME: ('a'..'z'|'A'..'Z')+ ; */
        @Override public boolean isSatisfiedBy(Character character) {
            return character >= 'a' && character <= 'z' ||
                    character >= 'A' && character <= 'Z';
        }
    },
    WHITESPACE("WHITESPACE") {
        /* WHITESPACE : (' '|'\t'|'\n'|'\r')* ;  */
        @Override public boolean isSatisfiedBy(Character character) {
            return character == ' ' ||
                    character == '\t' ||
                    character == '\n' ||
                    character == '\r';
        }
    },
    COMMA("COMMA") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == ',';
        }
    },
    LBRACK("LBRACK") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == '[';
        }
    },
    RBRACK("RBRACK") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == ']';
        }
    },
    EOF("<EOF>") {
        @Override public boolean isSatisfiedBy(Character character) {
            return character == Constants.EOF_CHARACTER;
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

    public abstract boolean isSatisfiedBy(Character character);

    public String getName() {
        return name;
    }
}
