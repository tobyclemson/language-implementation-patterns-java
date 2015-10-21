package com.tobyclemson.lip.refactored.pattern4.selectors;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.PhraseSelector;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.common.TokenType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TwoTokenPhraseSelector implements PhraseSelector {
    TokenType firstToken;
    TokenType secondToken;

    @Override
    public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        return lookaheadBuffer.lookahead(1).isOfType(firstToken) &&
                lookaheadBuffer.lookahead(2).isOfType(secondToken);
    }
}
