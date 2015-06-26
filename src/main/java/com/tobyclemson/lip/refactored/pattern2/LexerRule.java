package com.tobyclemson.lip.refactored.pattern2;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;

public interface LexerRule {
    boolean isApplicableTo(LookaheadBuffer<Character> lookaheadBuffer);
    Token apply(LookaheadBuffer<Character> lookaheadBuffer);
}
