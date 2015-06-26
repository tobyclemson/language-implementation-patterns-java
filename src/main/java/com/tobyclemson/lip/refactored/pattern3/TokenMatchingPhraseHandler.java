package com.tobyclemson.lip.refactored.pattern3;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.pattern2.Token;
import com.tobyclemson.lip.refactored.pattern2.TokenType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenMatchingPhraseHandler implements PhraseHandler {
    TokenType expectedTokenType;

    @Override public void handle(LookaheadBuffer<Token> lookaheadBuffer) {
        Token lookaheadToken = lookaheadBuffer.getLookahead();
        if (lookaheadToken.isOfType(expectedTokenType)) {
            lookaheadBuffer.advance();
        } else {
            throw new Error(
                    "expecting " + expectedTokenType.getName() +
                            "; found " + lookaheadToken.getTypeName());
        }
    }
}
