package com.tobyclemson.lip.refactored.pattern2;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;

public class SingleCharacterRule extends TypeBasedRule {
    public SingleCharacterRule(TokenType tokenType) {
        super(tokenType);
    }

    @Override public String extractFrom(LookaheadBuffer lookaheadBuffer) {
        String content = String.valueOf(lookaheadBuffer.getLookahead());
        lookaheadBuffer.advance();
        return content;
    }
}
