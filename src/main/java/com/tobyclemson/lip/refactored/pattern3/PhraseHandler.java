package com.tobyclemson.lip.refactored.pattern3;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.pattern2.Token;

public interface PhraseHandler {
    void handle(LookaheadBuffer<Token> lookaheadBuffer);
}
