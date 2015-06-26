package com.tobyclemson.lip.refactored.pattern3;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.pattern2.Token;
import com.tobyclemson.lip.refactored.pattern2.TokenType;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SingleTokenPhrase implements Phrase {
    PhraseHandler tokenMatchingPhraseHandler;
    TokenType type;

    public SingleTokenPhrase(TokenType type) {
        this.type = type;
        this.tokenMatchingPhraseHandler = new TokenMatchingPhraseHandler(type);
    }

    @Override public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return lookaheadBuffer.getLookahead().isOfType(type);
    }

    @Override public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        tokenMatchingPhraseHandler.handle(lookaheadBuffer);
    }
}
