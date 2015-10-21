package com.tobyclemson.lip.refactored.pattern3.phrases;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Phrase;
import com.tobyclemson.lip.refactored.common.Token;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LazilyConstructedPhrase implements Phrase {
    public interface Factory {
        Phrase get();
    }

    Factory factory;

    @Override public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return factory.get().isSelectedBy(lookaheadBuffer);
    }

    @Override public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        factory.get().apply(lookaheadBuffer);
    }
}
