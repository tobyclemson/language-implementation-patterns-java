package com.tobyclemson.lip.refactored.pattern2;

import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.pattern2.lexers.FilteringLexer;
import com.tobyclemson.lip.refactored.pattern2.lexers.RuleBasedLexer;
import com.tobyclemson.lip.refactored.pattern2.rules.MultiCharacterRule;
import com.tobyclemson.lip.refactored.pattern2.rules.SingleCharacterRule;
import org.javafunk.funk.Literals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.junit.Assert.assertThat;

public class Pattern2Test {
    /**
     * list     : '[' elements ']' ;       // match bracketed list
     * elements : element (',' element)* ; // match comma-separated list
     * element  : NAME | list ;            // element is name or nested list
     * NAME     : ('a'..'z'|'A'..'Z')+ ;   // NAME is sequence of >=1 letter
     */

    @Test
    public void tokenisesListOfDepthOneUsingListGrammar() {
        // Given
        String input = "[a, b ]";
        Lexer lexer = listLexerFor(input);
        List<Token> expectedTokens = Literals.listWith(
                new Token(TokenTypes.LBRACK, "["),
                new Token(TokenTypes.NAME, "a"),
                new Token(TokenTypes.COMMA, ","),
                new Token(TokenTypes.NAME, "b"),
                new Token(TokenTypes.RBRACK, "]"),
                new Token(TokenTypes.EOF, "<EOF>"));

        // When
        List<Token> actualTokens = tokensFrom(lexer);

        // Then
        assertThat(actualTokens, is(expectedTokens));
    }

    @Test
    public void tokenisesNestedListsUsingListGrammar() {
        // Given
        String input = "[ant, [bear, [cat]], dog]";
        Lexer lexer = listLexerFor(input);

        Token leftBracket = new Token(TokenTypes.LBRACK, "[");
        Token rightBracket = new Token(TokenTypes.RBRACK, "]");
        Token comma = new Token(TokenTypes.COMMA, ",");
        Token endOfFile = new Token(TokenTypes.EOF, "<EOF>");
        List<Token> expectedTokens = Literals.listWith(
                leftBracket,
                new Token(TokenTypes.NAME, "ant"),
                comma,
                leftBracket,
                new Token(TokenTypes.NAME, "bear"),
                comma,
                leftBracket,
                new Token(TokenTypes.NAME, "cat"),
                rightBracket,
                rightBracket,
                comma,
                new Token(TokenTypes.NAME, "dog"),
                rightBracket,
                endOfFile);

        // When
        List<Token> actualTokens = tokensFrom(lexer);

        // Then
        assertThat(actualTokens, is(expectedTokens));
    }

    private List<Token> tokensFrom(Lexer lexer) {
        List<Token> tokens = new ArrayList<Token>();
        Token token = lexer.nextToken();
        while (token.type != TokenTypes.EOF) {
            tokens.add(token);
            token = lexer.nextToken();
        }
        tokens.add(token);
        return tokens;
    }

    public Lexer listLexerFor(String input) {
        LexerRule eofRule = new SingleCharacterRule(TokenTypes.EOF);
        LexerRule whitespaceRule = new MultiCharacterRule(TokenTypes.WHITESPACE);
        LexerRule commaRule = new SingleCharacterRule(TokenTypes.COMMA);
        LexerRule leftBracketRule = new SingleCharacterRule(TokenTypes.LBRACK);
        LexerRule rightBracketRule = new SingleCharacterRule(TokenTypes.RBRACK);
        LexerRule nameRule = new MultiCharacterRule(TokenTypes.NAME);

        return new FilteringLexer(
                TokenTypes.WHITESPACE,
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
