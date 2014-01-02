package com.tobyclemson.lip.pattern2;

public abstract class Lexer {
    public static final char EOF = (char)-1; // represent end of file char
    public static final int EOF_TYPE = 1;    // represent EOF token type

    private final String input;      // input string
    private int p = 0;           // p into input of current character
    protected char c;

    Lexer(String input) {
        this.input = input;
        c = input.charAt(p);
    }

    /**
     * Move one character; detect "end of file"
     */
    public void consume() {
        p++;
        if (p >= input.length()) {
            c = EOF;
        } else {
            c = input.charAt(p);
        }
    }

    /**
     * Ensure the supplied character is the next on the input stream
     * @param expectedCharacter Expected character
     */
    public void match(char expectedCharacter) {
        if (c == expectedCharacter) {
            consume();
        } else {
            throw new Error(
                    "Expecting " + expectedCharacter + ", " +
                            "found " + c);
        }
    }

    public abstract Token nextToken();
    public abstract String getTokenName(int tokenType);
}
