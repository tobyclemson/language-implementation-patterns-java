package com.tobyclemson.lip.pattern2;

public class ListLexer extends Lexer {
    public static int NAME = 2;
    public static int COMMA = 3;
    public static int LBRACK = 4;
    public static int RBRACK = 5;

    public static String[] tokenNames = {
            "n/a", "<EOF>", "NAME", "COMMA", "LBRACK", "RBRACK"
    };

    public ListLexer(String input) {
        super(input);
    }

    private boolean isLETTER() {
        return c >= 'a' && c <= 'z' ||
                c >= 'A' && c <= 'Z';
    }

    @Override public String getTokenName(int x) {
        return tokenNames[x];
    }

    @Override public Token nextToken() {
        while (c != EOF) {
            switch (c) {
                case ' ': case '\t': case '\n': case '\r':
                    WHITESPACE();
                    continue;
                case ',':
                    consume();
                    return new Token(COMMA, ",");
                case '[':
                    consume();
                    return new Token(LBRACK, "[");
                case ']':
                    consume();
                    return new Token(RBRACK, "]");
                default:
                    if (isLETTER()) {
                        return NAME();
                    }
                    throw new Error("Invalid character: " + c);
            }
        }
        return new Token(EOF_TYPE, "<EOF>");
    }

    /* NAME: ('a'..'z'|'A'..'Z')+ ; // NAME is a sequence of >= 2 letter */
    private Token NAME() {
        StringBuilder buffer = new StringBuilder();
        do {
            buffer.append(c);
            consume();
        } while (isLETTER());
        return new Token(NAME, buffer.toString());
    }

    /* WS : (' '|'\t'|'\n'|'\r')* ; // ignore any whitespace */
    private void WHITESPACE() {
        while (c == ' ' ||
                c == '\t' ||
                c == '\n' ||
                c == '\r') {
            consume();
        }
    }
}
