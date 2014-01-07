package com.tobyclemson.lip.pattern2refactored;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tobyclemson.lip.pattern2refactored.TokenType.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Pattern2Test {
    /**
     * list     : '[' elements ']' ;       // match bracketed list
     * elements : element (',' element)* ; // match comma-separated list
     * element  : NAME | list ;            // element is name or nested list
     * NAME     : ('a'..'z'|'A'..'Z')+ ;   // NAME is sequence of >=1 letter
     */
    @Test
    public void tokenisesListGrammar() {
        // Given
        String input = "[a, b ]";
        Lexer lexer = lexerFor(input);
        List<Token> expectedTokens = listWith(
                new Token(LBRACK, "["),
                new Token(NAME, "a"),
                new Token(COMMA, ","),
                new Token(NAME, "b"),
                new Token(RBRACK, "]"),
                new Token(EOF, "<EOF>"));

        // When
        List<Token> actualTokens = tokensFrom(lexer);

        // Then
        assertThat(actualTokens, is(expectedTokens));
    }

    private Lexer lexerFor(String input) {
        ArrayList<LexerRule> rules = new ArrayList<LexerRule>();

        LexerRule eofRule = new SingleCharacterRule(EOF);
        LexerRule whitespaceRule = new MultiCharacterRule(WHITESPACE);
        LexerRule commaRule = new SingleCharacterRule(COMMA);
        LexerRule leftBracketRule = new SingleCharacterRule(LBRACK);
        LexerRule rightBracketRule = new SingleCharacterRule(RBRACK);
        LexerRule nameRule = new MultiCharacterRule(NAME);

        rules.add(eofRule);
        rules.add(whitespaceRule);
        rules.add(commaRule);
        rules.add(leftBracketRule);
        rules.add(rightBracketRule);
        rules.add(nameRule);

        return new WhitespaceFilteringLexer(new RuleBasedLexer(input, rules));
    }

    private List<Token> listWith(Token... tokens) {
        List<Token> expectedTokens = new ArrayList<Token>();
        Collections.addAll(expectedTokens, tokens);
        return expectedTokens;
    }

    private List<Token> tokensFrom(Lexer lexer) {
        List<Token> tokens = new ArrayList<Token>();
        Token token = lexer.nextToken();
        while (token.type != EOF) {
            tokens.add(token);
            token = lexer.nextToken();
        }
        tokens.add(token);
        return tokens;
    }

}
