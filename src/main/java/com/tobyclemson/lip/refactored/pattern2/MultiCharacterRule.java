package com.tobyclemson.lip.refactored.pattern2;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;

public class MultiCharacterRule extends TypeBasedRule {
    public MultiCharacterRule(TokenType tokenType) {
        super(tokenType);
    }

    @Override public String extractFrom(LookaheadBuffer<Character> lookaheadBuffer) {
        StringBuilder buffer = new StringBuilder();
        do {
            buffer.append(lookaheadBuffer.getLookahead());
            lookaheadBuffer.advance();
        } while (this.isApplicableTo(lookaheadBuffer));
        return buffer.toString();
    }
}
