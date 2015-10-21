package com.tobyclemson.lip.refactored.pattern2.rules;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.common.TokenType;
import com.tobyclemson.lip.refactored.pattern2.LexerRule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class TypeBasedRule implements LexerRule {
    TokenType tokenType;

    @Override public boolean isApplicableTo(LookaheadBuffer<Character> lookaheadBuffer) {
        return tokenType.isSatisfiedBy(lookaheadBuffer.lookahead());
    }

    @Override public Token apply(LookaheadBuffer<Character> lookaheadBuffer) {
        return tokenType.token(extractFrom(lookaheadBuffer));
    }

    public abstract String extractFrom(LookaheadBuffer<Character> lookaheadBuffer);
}

