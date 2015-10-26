package com.tobyclemson.lip.refactored.pattern5;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Phrase;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.pattern2.Lexer;
import com.tobyclemson.lip.refactored.pattern2.LexerRule;
import com.tobyclemson.lip.refactored.pattern2.lexers.FilteringLexer;
import com.tobyclemson.lip.refactored.pattern2.lexers.RuleBasedLexer;
import com.tobyclemson.lip.refactored.pattern2.rules.MultiCharacterRule;
import com.tobyclemson.lip.refactored.pattern2.rules.SingleCharacterRule;
import com.tobyclemson.lip.refactored.pattern3.Parser;
import com.tobyclemson.lip.refactored.pattern3.PhraseBasedParser;
import com.tobyclemson.lip.refactored.pattern3.TokenReader;
import com.tobyclemson.lip.refactored.pattern3.exceptions.RecognitionException;
import com.tobyclemson.lip.refactored.pattern3.phrases.*;
import com.tobyclemson.lip.refactored.pattern4.handlers.MultiTokenMatchingPhraseHandler;
import com.tobyclemson.lip.refactored.pattern4.phrases.DelegatingPhrase;
import com.tobyclemson.lip.refactored.pattern4.selectors.TwoTokenPhraseSelector;
import com.tobyclemson.lip.refactored.pattern5.phrases.SpeculationPhrase;
import com.tobyclemson.lip.refactored.pattern5.phrases.SpeculationSetPhrase;
import org.junit.Test;

import static org.javafunk.funk.Literals.iterableWith;

public class Pattern5Test {
    /**
     * stat     : list EOF | assign EOF ;
     * assign   : list '=' list ;
     * list     : '[' elements ']' ;            // match bracketed list
     * elements : element (',' element)* ;      // match comma-separated list
     * element  : NAME '=' NAME | NAME | list ; //element is name, nested list
     */

    @Test
    public void parsesParallelListAssignmentsWithoutError() {
        // Given
        String input = "[ant, bear] = [cow, pig]";
        Parser parser = listParserFor(input);

        // When
        parser.parse();

        // Then no exceptions are thrown
    }

    @Test(expected = RecognitionException.class)
    public void throwsErrorForInvalidInput() {
        // Given
        String input = "[ant, bear], [cow, pig]";
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
        LookaheadBuffer<Token> lookaheadBuffer = new LookaheadBuffer<>(tokenReader);

        Phrase equalsPhrase = new SingleTokenPhrase(TokenTypes.EQUALS);
        Phrase eofPhrase = new SingleTokenPhrase(TokenTypes.EOF);
        Phrase listPhrase = listPhrase().get();

        /** assign : list '=' list ; // parallel assignment */
        Phrase topLevelAssignmentPhrase = new CompositionPhrase(listPhrase, equalsPhrase, listPhrase);

        /** stat : list EOF | assign EOF ; */
        Phrase statementPhrase = new SpeculationSetPhrase(
                new SpeculationPhrase(new CompositionPhrase(topLevelAssignmentPhrase, eofPhrase)),
                new SpeculationPhrase(new CompositionPhrase(listPhrase, eofPhrase)));

        return new PhraseBasedParser(lookaheadBuffer, statementPhrase);
    }

    private LazilyConstructedPhrase.Factory listPhrase() {
        return () -> {
            Phrase leftBracketPhrase = new SingleTokenPhrase(TokenTypes.LBRACK);
            Phrase rightBracketPhrase = new SingleTokenPhrase(TokenTypes.RBRACK);
            Phrase namePhrase = new SingleTokenPhrase(TokenTypes.NAME);

            Phrase nestedAssignmentPhrase = new DelegatingPhrase(
                    new TwoTokenPhraseSelector(TokenTypes.NAME, TokenTypes.EQUALS),
                    new MultiTokenMatchingPhraseHandler(TokenTypes.NAME, TokenTypes.EQUALS, TokenTypes.NAME));

            /** list : '[' elements ']' ; // match bracketed list */
            Phrase listPhrase = new LazilyConstructedPhrase(listPhrase());

            /** element : NAME '=' NAME | NAME | list ; assignment, NAME or list */
            Phrase elementPhrase = new AlternationPhrase(
                    nestedAssignmentPhrase,
                    namePhrase,
                    listPhrase);

            /** elements : element (',' element)* ; */
            Phrase elementsPhrase = new RepetitionPhrase(elementPhrase, TokenTypes.COMMA);

            return new CompositionPhrase(leftBracketPhrase, elementsPhrase, rightBracketPhrase);
        };
    }
}

