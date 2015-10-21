package com.tobyclemson.lip.refactored.pattern4.handlers;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.PhraseHandler;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.common.TokenType;
import com.tobyclemson.lip.refactored.pattern3.handlers.OneTokenMatchingPhraseHandler;
import org.javafunk.funk.Eagerly;

import static org.javafunk.funk.Literals.iterableFrom;

public class MultiTokenMatchingPhraseHandler implements PhraseHandler {
    Iterable<PhraseHandler> delegates;

    public MultiTokenMatchingPhraseHandler(TokenType... tokenTypes) {
        this(iterableFrom(tokenTypes));
    }

    public MultiTokenMatchingPhraseHandler(Iterable<TokenType> tokenTypes) {
        this.delegates = Eagerly.map(tokenTypes, OneTokenMatchingPhraseHandler::new);
    }

    @Override
    public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        Eagerly.each(delegates, delegate -> delegate.apply(lookaheadBuffer));
    }
}
