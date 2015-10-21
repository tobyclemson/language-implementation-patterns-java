package com.tobyclemson.lip.refactored.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LookaheadBuffer<T> {
    Reader<T> reader;
    @Getter @NonFinal T lookahead;

    public LookaheadBuffer(Reader<T> reader) {
        this.reader = reader;
        lookahead = reader.readNext();
    }

    public void advance() {
        lookahead = reader.readNext();
    }
}
