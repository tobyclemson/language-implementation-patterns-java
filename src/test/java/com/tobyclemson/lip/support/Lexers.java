package com.tobyclemson.lip.support;

import com.tobyclemson.lip.refactored.pattern2.*;

import static com.tobyclemson.lip.refactored.pattern2.TokenType.*;
import static org.javafunk.funk.Literals.iterableWith;

public class Lexers {
    public static Lexer listLexerFor(String input) {
        LexerRule eofRule = new SingleCharacterRule(EOF);
        LexerRule whitespaceRule = new MultiCharacterRule(WHITESPACE);
        LexerRule commaRule = new SingleCharacterRule(COMMA);
        LexerRule leftBracketRule = new SingleCharacterRule(LBRACK);
        LexerRule rightBracketRule = new SingleCharacterRule(RBRACK);
        LexerRule nameRule = new MultiCharacterRule(NAME);

        return new WhitespaceFilteringLexer(
                new RuleBasedLexer(input,
                        iterableWith(
                                eofRule,
                                whitespaceRule,
                                commaRule,
                                leftBracketRule,
                                rightBracketRule,
                                nameRule)));
    }
}
