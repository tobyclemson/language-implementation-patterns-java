package com.tobyclemson.lip.refactored.pattern2;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class TypeBasedRule implements LexerRule {
    TokenType tokenType;

    @Override public boolean isApplicableTo(LookaheadBuffer<Character> lookaheadBuffer) {
        return tokenType.isSatisfiedBy(lookaheadBuffer.getLookahead());
    }

    @Override public Token apply(LookaheadBuffer lookaheadBuffer) {
        return tokenType.token(extractFrom(lookaheadBuffer));
    }

    public abstract String extractFrom(LookaheadBuffer lookaheadBuffer);
}

