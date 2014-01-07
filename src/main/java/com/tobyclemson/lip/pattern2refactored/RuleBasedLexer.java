package com.tobyclemson.lip.pattern2refactored;

public class RuleBasedLexer implements Lexer {
    private final LookaheadBuffer lookaheadBuffer;
    private final Iterable<LexerRule> rules;

    public RuleBasedLexer(String input, Iterable<LexerRule> rules) {
        this.lookaheadBuffer = new LookaheadBuffer(input);
        this.rules = rules;

    }

    @Override
    public Token nextToken() {
        for (LexerRule rule : rules) {
            if (rule.isApplicableTo(lookaheadBuffer)) {
                return rule.apply(lookaheadBuffer);
            }
        }
        throw new Error("Invalid character: " + lookaheadBuffer.getLookaheadCharacter());
    }
}
