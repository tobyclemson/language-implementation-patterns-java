package com.tobyclemson.lip.refactored.pattern3;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.pattern2.Token;
import com.tobyclemson.lip.refactored.pattern2.TokenType;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OneOrMorePhrase implements Phrase {
    Phrase phrase;
    TokenType moreTokenType;
    PhraseHandler moreTokenHandler;

    public OneOrMorePhrase(Phrase phrase, TokenType moreTokenType) {
        this.phrase = phrase;
        this.moreTokenType = moreTokenType;
        this.moreTokenHandler = new TokenMatchingPhraseHandler(moreTokenType);
    }

    @Override public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return phrase.isSelectedBy(lookaheadBuffer);
    }

    @Override public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        phrase.apply(lookaheadBuffer);
        while (lookaheadBuffer.getLookahead().isOfType(moreTokenType)) {
            moreTokenHandler.handle(lookaheadBuffer);
            phrase.apply(lookaheadBuffer);
        }
    }
}
