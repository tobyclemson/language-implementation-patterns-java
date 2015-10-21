package com.tobyclemson.lip.refactored.pattern2.rules;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.TokenType;

public class MultiCharacterRule extends TypeBasedRule {
    public MultiCharacterRule(TokenType tokenType) {
        super(tokenType);
    }

    @Override public String extractFrom(LookaheadBuffer<Character> lookaheadBuffer) {
        StringBuilder buffer = new StringBuilder();
        do {
            buffer.append(lookaheadBuffer.lookahead());
            lookaheadBuffer.advance();
        } while (this.isApplicableTo(lookaheadBuffer));
        return buffer.toString();
    }
}
