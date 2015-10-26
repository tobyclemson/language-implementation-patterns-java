package com.tobyclemson.lip.refactored.common;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LookaheadBuffer<T> {
    Reader<T> reader;
    MarkReleaseBuffer<T> lookahead;

    public LookaheadBuffer(Reader<T> reader) {
        this.reader = reader;
        this.lookahead = new MarkReleaseBuffer<>(reader);
    }

    public void advance() {
        lookahead.advance();
    }

    public T lookahead() {
        return lookahead.peek(0);
    }

    public T lookahead(Integer by) {
        return lookahead.peek(by - 1);
    }

    public void mark() {
        lookahead.mark();
    }

    public void release() {
        lookahead.release();
    }

    public boolean isMarked() {
        return lookahead.isMarked();
    }
}
