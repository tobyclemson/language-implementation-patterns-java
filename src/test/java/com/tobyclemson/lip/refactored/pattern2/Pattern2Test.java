package com.tobyclemson.lip.refactored.pattern2;

import com.tobyclemson.lip.support.Lexers;
import org.javafunk.funk.Literals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.tobyclemson.lip.refactored.pattern2.TokenType.*;
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
    public void tokenisesListOfDepthOneUsingListGrammar() {
        // Given
        String input = "[a, b ]";
        Lexer lexer = Lexers.listLexerFor(input);
        List<Token> expectedTokens = Literals.listWith(
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

    @Test
    public void tokenisesNestedListsUsingListGrammar() {
        // Given
        String input = "[ant, [bear, [cat]], dog]";
        Lexer lexer = Lexers.listLexerFor(input);

        Token leftBracket = new Token(LBRACK, "[");
        Token rightBracket = new Token(RBRACK, "]");
        Token comma = new Token(COMMA, ",");
        Token endOfFile = new Token(EOF, "<EOF>");
        List<Token> expectedTokens = Literals.listWith(
                leftBracket,
                new Token(NAME, "ant"),
                comma,
                leftBracket,
                new Token(NAME, "bear"),
                comma,
                leftBracket,
                new Token(NAME, "cat"),
                rightBracket,
                rightBracket,
                comma,
                new Token(NAME, "dog"),
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
        while (token.type != EOF) {
            tokens.add(token);
            token = lexer.nextToken();
        }
        tokens.add(token);
        return tokens;
    }

}
