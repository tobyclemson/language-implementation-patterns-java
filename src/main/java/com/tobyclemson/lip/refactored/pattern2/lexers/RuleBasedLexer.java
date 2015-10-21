package com.tobyclemson.lip.refactored.pattern2.lexers;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.pattern2.Lexer;
import com.tobyclemson.lip.refactored.pattern2.LexerRule;
import com.tobyclemson.lip.refactored.pattern2.StringReader;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RuleBasedLexer implements Lexer {
    LookaheadBuffer<Character> lookaheadBuffer;
    Iterable<LexerRule> rules;

    public RuleBasedLexer(String input, Iterable<LexerRule> rules) {
        this(new LookaheadBuffer<>(new StringReader(input)), rules);
    }

    @Override
    public Token nextToken() {
        for (LexerRule rule : rules) {
            if (rule.isApplicableTo(lookaheadBuffer)) {
                return rule.apply(lookaheadBuffer);
            }
        }
        throw new Error("Invalid character: " + lookaheadBuffer.lookahead());
    }
}
