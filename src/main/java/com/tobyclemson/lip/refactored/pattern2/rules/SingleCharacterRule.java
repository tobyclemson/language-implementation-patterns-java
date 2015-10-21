package com.tobyclemson.lip.refactored.pattern2.rules;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.TokenType;

public class SingleCharacterRule extends TypeBasedRule {
    public SingleCharacterRule(TokenType tokenType) {
        super(tokenType);
    }

    @Override public String extractFrom(LookaheadBuffer lookaheadBuffer) {
        String content = String.valueOf(lookaheadBuffer.lookahead());
        lookaheadBuffer.advance();
        return content;
    }
}
