package com.tobyclemson.lip.refactored.common;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.javafunk.funk.Eagerly;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LookaheadBuffer<T> {
    Reader<T> reader;
    CircularQueue<T> lookahead;

    public LookaheadBuffer(Reader<T> reader) {
        this(1, reader);
    }

    public LookaheadBuffer(Integer maximumLookahead, Reader<T> reader) {
        this.reader = reader;
        this.lookahead = new CircularQueue<>(maximumLookahead);
        Eagerly.times(maximumLookahead, i -> lookahead.push(reader.readNext()));
    }

    public void advance() {
        lookahead.pop();
        lookahead.push(reader.readNext());
    }

    public T lookahead() {
        return lookahead.peek(0);
    }

    public T lookahead(Integer by) {
        return lookahead.peek(by - 1);
    }
}
