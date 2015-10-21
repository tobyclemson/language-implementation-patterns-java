package com.tobyclemson.lip.refactored.pattern3.handlers;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.PhraseHandler;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.common.TokenType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OneTokenMatchingPhraseHandler implements PhraseHandler {
    TokenType expectedTokenType;

    @Override public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        Token lookaheadToken = lookaheadBuffer.lookahead();
        if (lookaheadToken.isOfType(expectedTokenType)) {
            lookaheadBuffer.advance();
        } else {
            throw new Error(
                    "expecting " + expectedTokenType.getName() +
                            "; found " + lookaheadToken.getTypeName());
        }
    }
}
