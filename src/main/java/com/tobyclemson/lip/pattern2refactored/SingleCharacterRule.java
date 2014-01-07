package com.tobyclemson.lip.pattern2refactored;

public class SingleCharacterRule extends TypeBasedRule {
    public SingleCharacterRule(TokenType tokenType) {
        super(tokenType);
    }

    @Override public String extractFrom(LookaheadBuffer lookaheadBuffer) {
        String content = String.valueOf(lookaheadBuffer.getLookaheadCharacter());
        lookaheadBuffer.advance();
        return content;
    }
}
