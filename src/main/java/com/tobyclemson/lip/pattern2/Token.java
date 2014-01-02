package com.tobyclemson.lip.pattern2;

public class Token {
    public int type;
    public String text;

    Token(int type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override public String toString() {
        String tname = ListLexer.tokenNames[type];
        return "<'" + text + "'," + tname + ">";
    }
}
