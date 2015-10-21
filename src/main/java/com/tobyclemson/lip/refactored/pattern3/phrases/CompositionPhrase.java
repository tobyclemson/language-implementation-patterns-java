package com.tobyclemson.lip.refactored.pattern3.phrases;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Phrase;
import com.tobyclemson.lip.refactored.common.Token;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.javafunk.funk.Eagerly;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompositionPhrase implements Phrase {
    Iterable<Phrase> phrases;

    @Override public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return Eagerly.first(phrases)
                .map(phrase -> phrase.isSelectedBy(lookaheadBuffer))
                .getOrElse(false);
    }

    @Override public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        Eagerly.each(phrases, phrase -> phrase.apply(lookaheadBuffer));
    }
}
