package com.tobyclemson.lip.refactored.common;

public class LookaheadBuffer<T> {
    private final Reader<T> reader;
    private T lookahead;

    public LookaheadBuffer(Reader<T> reader) {
        this.reader = reader;
        lookahead = reader.readNext();
    }

    public T getLookahead() {
        return lookahead;
    }

    public void advance() {
        lookahead = reader.readNext();
    }
}
