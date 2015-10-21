package com.tobyclemson.lip.refactored.pattern3.selectors;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.PhraseSelector;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.common.TokenType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OneTokenPhraseSelector implements PhraseSelector {
    TokenType tokenType;

    @Override
    public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return lookaheadBuffer.lookahead().isOfType(tokenType);
    }
}
