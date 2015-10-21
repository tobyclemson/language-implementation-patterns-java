package com.tobyclemson.lip.refactored.pattern3.phrases;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Phrase;
import com.tobyclemson.lip.refactored.common.PhraseHandler;
import com.tobyclemson.lip.refactored.common.PhraseSelector;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.common.TokenType;
import com.tobyclemson.lip.refactored.pattern3.handlers.OneTokenMatchingPhraseHandler;
import com.tobyclemson.lip.refactored.pattern3.selectors.OneTokenPhraseSelector;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SingleTokenPhrase implements Phrase {
    PhraseHandler handler;
    PhraseSelector selector;

    public SingleTokenPhrase(TokenType type) {
        this.selector = new OneTokenPhraseSelector(type);
        this.handler = new OneTokenMatchingPhraseHandler(type);
    }

    @Override public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return selector.isSelectedBy(lookaheadBuffer);
    }

    @Override public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        handler.apply(lookaheadBuffer);
    }
}
