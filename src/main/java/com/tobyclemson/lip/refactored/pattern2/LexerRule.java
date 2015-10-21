package com.tobyclemson.lip.refactored.pattern2;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Token;

public interface LexerRule {
    boolean isApplicableTo(LookaheadBuffer<Character> lookaheadBuffer);
    Token apply(LookaheadBuffer<Character> lookaheadBuffer);
}
