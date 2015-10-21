package com.tobyclemson.lip.refactored.common;

public interface PhraseSelector {
    boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer);
}
