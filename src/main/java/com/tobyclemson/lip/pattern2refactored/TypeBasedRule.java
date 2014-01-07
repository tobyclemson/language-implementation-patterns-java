package com.tobyclemson.lip.pattern2refactored;

public abstract class TypeBasedRule implements LexerRule {
    protected final TokenType tokenType;

    public TypeBasedRule(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override public boolean isApplicableTo(LookaheadBuffer lookaheadBuffer) {
        return tokenType.isSatisfiedBy(lookaheadBuffer.getLookaheadCharacter());
    }

    @Override public Token apply(LookaheadBuffer lookaheadBuffer) {
        return tokenType.token(extractFrom(lookaheadBuffer));

    }

    public abstract String extractFrom(LookaheadBuffer lookaheadBuffer);
}

