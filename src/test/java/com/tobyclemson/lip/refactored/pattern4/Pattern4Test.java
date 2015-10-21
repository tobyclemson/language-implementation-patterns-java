package com.tobyclemson.lip.refactored.pattern4;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.pattern2.*;
import com.tobyclemson.lip.refactored.pattern2.lexers.FilteringLexer;
import com.tobyclemson.lip.refactored.pattern2.lexers.RuleBasedLexer;
import com.tobyclemson.lip.refactored.pattern2.rules.MultiCharacterRule;
import com.tobyclemson.lip.refactored.pattern2.rules.SingleCharacterRule;
import com.tobyclemson.lip.refactored.pattern3.*;
import com.tobyclemson.lip.refactored.pattern3.phrases.*;
import com.tobyclemson.lip.refactored.pattern4.handlers.MultiTokenMatchingPhraseHandler;
import com.tobyclemson.lip.refactored.pattern4.phrases.DelegatingPhrase;
import com.tobyclemson.lip.refactored.pattern4.selectors.TwoTokenPhraseSelector;
import org.junit.Test;

import static org.javafunk.funk.Literals.iterableWith;

public class Pattern4Test {
    /**
     * list     : '[' elements ']' ;            // match bracketed list
     * elements : element (',' element)* ;      // match comma-separated list
     * element  : NAME '=' NAME | NAME | list ; // element is assignment, name or nested list
     * NAME     : ('a'..'z'|'A'..'Z')+ ;        // NAME is sequence of >=1 letter
     */

    @Test public void parsesListOfDepthOneWithoutError() {
        // Given
        String input = "[a, b ]";
        Parser parser = listParserFor(input);

        // When
        parser.parse();

        // Then no exceptions are thrown
    }

    @Test public void parsesNestedListsWithoutError() {
        // Given
        String input = "[ant, [bear, [cat]], dog]";
        Parser parser = listParserFor(input);

        // When
        parser.parse();

        // Then no exceptions are thrown
    }

    @Test public void parsesListAssignmentsWithoutError() {
        // Given
        String input = "[ant, bear=cat, dog]";
        Parser parser = listParserFor(input);

        // When
        parser.parse();

        // Then no exceptions are thrown
    }

    @Test public void parsesNestedListAssignmentsWithoutError() {
        // Given
        String input = "[ant, [bear=cat, cow=pig, [squirrel], dog]]";
        Parser parser = listParserFor(input);

        // When
        parser.parse();

        // Then no exceptions are thrown
    }

    public Lexer listLexerFor(String input) {
        LexerRule eofRule = new SingleCharacterRule(TokenTypes.EOF);
        LexerRule whitespaceRule = new MultiCharacterRule(TokenTypes.WHITESPACE);
        LexerRule commaRule = new SingleCharacterRule(TokenTypes.COMMA);
        LexerRule leftBracketRule = new SingleCharacterRule(TokenTypes.LBRACK);
        LexerRule rightBracketRule = new SingleCharacterRule(TokenTypes.RBRACK);
        LexerRule nameRule = new MultiCharacterRule(TokenTypes.NAME);
        LexerRule equalsRule = new SingleCharacterRule(TokenTypes.EQUALS);

        return new FilteringLexer(
                TokenTypes.WHITESPACE,
                new RuleBasedLexer(input,
                        iterableWith(
                                eofRule,
                                whitespaceRule,
                                commaRule,
                                leftBracketRule,
                                rightBracketRule,
                                nameRule,
                                equalsRule)));
    }

    public Parser listParserFor(String input) {
        Lexer lexer = listLexerFor(input);
        TokenReader tokenReader = new TokenReader(lexer);
        LookaheadBuffer<Token> lookaheadBuffer = new LookaheadBuffer<>(2, tokenReader);

        return new PhraseBasedParser(lookaheadBuffer, listPhrase().get());
    }

    private LazilyConstructedPhrase.Factory listPhrase() {
        return () -> {
            SingleTokenPhrase leftBracketPhrase = new SingleTokenPhrase(TokenTypes.LBRACK);
            SingleTokenPhrase rightBracketPhrase = new SingleTokenPhrase(TokenTypes.RBRACK);
            SingleTokenPhrase namePhrase = new SingleTokenPhrase(TokenTypes.NAME);
            DelegatingPhrase assignmentPhrase = new DelegatingPhrase(
                    new TwoTokenPhraseSelector(TokenTypes.NAME, TokenTypes.EQUALS),
                    new MultiTokenMatchingPhraseHandler(TokenTypes.NAME, TokenTypes.EQUALS, TokenTypes.NAME));

            /** list : '[' elements ']' ; // match bracketed list */
            LazilyConstructedPhrase listPhrase = new LazilyConstructedPhrase(listPhrase());

            /** element : NAME '=' NAME | NAME | list ; assignment, NAME or list */
            AlternationPhrase elementPhrase = new AlternationPhrase(
                    iterableWith(
                            assignmentPhrase,
                            namePhrase,
                            listPhrase));

            /** elements : element (',' element)* ; */
            RepetitionPhrase elementsPhrase = new RepetitionPhrase(elementPhrase, TokenTypes.COMMA);

            return new CompositionPhrase(
                    iterableWith(leftBracketPhrase, elementsPhrase, rightBracketPhrase));
        };
    }
}

