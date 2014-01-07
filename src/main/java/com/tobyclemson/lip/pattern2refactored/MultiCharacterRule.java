package com.tobyclemson.lip.pattern2refactored;

public class MultiCharacterRule extends TypeBasedRule {
    public MultiCharacterRule(TokenType tokenType) {
        super(tokenType);
    }

    @Override public String extractFrom(LookaheadBuffer lookaheadBuffer) {
        StringBuilder buffer = new StringBuilder();
        do {
            buffer.append(lookaheadBuffer.getLookaheadCharacter());
            lookaheadBuffer.advance();
        } while (this.isApplicableTo(lookaheadBuffer));
        return buffer.toString();
    }
}
