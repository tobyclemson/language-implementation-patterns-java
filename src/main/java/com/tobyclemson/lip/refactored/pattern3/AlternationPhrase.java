package com.tobyclemson.lip.refactored.pattern3;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.pattern2.Token;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.javafunk.funk.Eagerly;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlternationPhrase implements Phrase {
    Iterable<Phrase> phrases;

    @Override public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return Eagerly.any(phrases, phrase -> phrase.isSelectedBy(lookaheadBuffer));
    }

    @Override public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        Eagerly.firstMatching(phrases, phrase -> phrase.isSelectedBy(lookaheadBuffer)).get()
                .apply(lookaheadBuffer);
    }
}
