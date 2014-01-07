package com.tobyclemson.lip.pattern2refactored;

public class LookaheadBuffer {
    public static final char EOF_CHARACTER = (char)-1;

    private final String input;
    private int cursor = 0;
    private char lookaheadCharacter;

    LookaheadBuffer(String input) {
        this.input = input;
        lookaheadCharacter = input.charAt(cursor);
    }

    public char getLookaheadCharacter() {
        return lookaheadCharacter;
    }

    public void advance() {
        cursor++;
        if (cursor >= input.length()) {
            lookaheadCharacter = EOF_CHARACTER;
        } else {
            lookaheadCharacter = input.charAt(cursor);
        }
    }
}
