package com.tobyclemson.lip.refactored.pattern3.phrases;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Phrase;
import com.tobyclemson.lip.refactored.common.PhraseHandler;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.common.TokenType;
import com.tobyclemson.lip.refactored.pattern3.handlers.OneTokenMatchingPhraseHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RepetitionPhrase implements Phrase {
    Phrase phrase;
    TokenType repetitionTokenType;
    PhraseHandler repetitionTokenHandler;

    public RepetitionPhrase(Phrase repetitionPhrase, TokenType repetitionTokenType) {
        this.phrase = repetitionPhrase;
        this.repetitionTokenType = repetitionTokenType;
        this.repetitionTokenHandler = new OneTokenMatchingPhraseHandler(repetitionTokenType);
    }

    @Override public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return phrase.isSelectedBy(lookaheadBuffer);
    }

    @Override public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        phrase.apply(lookaheadBuffer);
        while (lookaheadBuffer.lookahead().isOfType(repetitionTokenType)) {
            repetitionTokenHandler.apply(lookaheadBuffer);
            phrase.apply(lookaheadBuffer);
        }
    }
}
