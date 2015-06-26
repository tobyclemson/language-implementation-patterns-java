package com.tobyclemson.lip.refactored.pattern3;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.pattern2.Token;

public interface Phrase {
    boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer);
    void apply(LookaheadBuffer<Token> lookaheadBuffer);
}
