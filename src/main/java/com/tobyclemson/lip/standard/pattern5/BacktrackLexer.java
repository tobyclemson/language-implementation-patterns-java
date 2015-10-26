package com.tobyclemson.lip.standard.pattern5;

import com.tobyclemson.lip.standard.pattern2.Lexer;
import com.tobyclemson.lip.standard.pattern2.Token;

public class BacktrackLexer extends Lexer {
    public static int NAME = 2;
    public static int EQUALS = 3;
    public static int COMMA = 4;
    public static int LBRACK = 5;
    public static int RBRACK = 6;

    public static String[] tokenNames = {
            "n/a", "<EOF>", "NAME", "EQUALS", "COMMA", "LBRACK", "RBRACK"
    };

    public BacktrackLexer(String input) {
        super(input);
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
                case '=':
                    consume();
                    return new Token(EQUALS, "=");
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

    /**
     * NAME: ('a'..'z'|'A'..'Z')+ ; // NAME is a sequence of >= 1 letter
     */
    private Token NAME() {
        StringBuilder buffer = new StringBuilder();
        do {
            buffer.append(c);
            consume();
        } while (isLETTER());
        return new Token(NAME, buffer.toString());
    }

    /**
     * WS : (' '|'\t'|'\n'|'\r')* ; // ignore any whitespace
     */
    private void WHITESPACE() {
        while (c == ' ' ||
                c == '\t' ||
                c == '\n' ||
                c == '\r') {
            consume();
        }
    }

    private boolean isLETTER() {
        return c >= 'a' && c <= 'z' ||
                c >= 'A' && c <= 'Z';
    }
}
