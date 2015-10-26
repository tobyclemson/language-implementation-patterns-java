package com.tobyclemson.lip.refactored.pattern5.phrases;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Phrase;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.pattern5.exceptions.NoViableAlternativeException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.javafunk.funk.Eagerly;
import org.javafunk.funk.monads.Option;

import static java.lang.String.format;
import static org.javafunk.funk.Literals.iterableFrom;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpeculationSetPhrase implements Phrase {
    Iterable<SpeculationPhrase> speculationPhrases;

    public SpeculationSetPhrase(SpeculationPhrase... speculationPhrases) {
        this(iterableFrom(speculationPhrases));
    }

    @Override
    public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return Eagerly.any(speculationPhrases, phrase -> phrase.isSelectedBy(lookaheadBuffer));
    }

    @Override
    public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        Option<SpeculationPhrase> speculationPhrases = Eagerly.firstMatching(this.speculationPhrases, phrase -> phrase.isSelectedBy(lookaheadBuffer));
        speculationPhrases
                .getOrThrow(new NoViableAlternativeException(format("found: %s", lookaheadBuffer.lookahead())))
                .apply(lookaheadBuffer);
    }
}
