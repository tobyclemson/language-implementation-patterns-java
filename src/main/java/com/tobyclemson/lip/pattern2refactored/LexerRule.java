package com.tobyclemson.lip.pattern2refactored;

public interface LexerRule {
    public boolean isApplicableTo(LookaheadBuffer lookaheadBuffer);
    public Token apply(LookaheadBuffer lookaheadBuffer);
}
