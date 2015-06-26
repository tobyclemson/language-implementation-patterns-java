package com.tobyclemson.lip.refactored.pattern2;

import com.tobyclemson.lip.refactored.common.Reader;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StringReader implements Reader<Character> {
    String input;
    @NonFinal Integer cursor;

    public StringReader(String input) {
        this.input = input;
        this.cursor = 0;
    }

    @Override public Character readNext() {
        if (cursor >= input.length()) {
            return Constants.EOF_CHARACTER;
        }
        Character next = input.charAt(cursor);
        cursor++;
        return next;
    }
}
