package com.tobyclemson.lip.standard.pattern2;

public class Token {
    public int type;
    public String text;

    public Token(int type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override public String toString() {
        String tname = ListLexer.tokenNames[type];
        return "<'" + text + "'," + tname + ">";
    }
}
