package com.tobyclemson.lip.support;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.pattern2.Lexer;
import com.tobyclemson.lip.refactored.pattern2.Token;
import com.tobyclemson.lip.refactored.pattern2.TokenType;
import com.tobyclemson.lip.refactored.pattern3.*;

import static com.tobyclemson.lip.support.Lexers.listLexerFor;
import static org.javafunk.funk.Literals.iterableWith;

public class Parsers {
    public static Parser listParserFor(String input) {
        Lexer lexer = listLexerFor(input);
        TokenReader tokenReader = new TokenReader(lexer);
        LookaheadBuffer<Token> lookaheadBuffer = new LookaheadBuffer<>(tokenReader);

        return new PhraseBasedParser(lookaheadBuffer, listPhrase().get());
    }

    private static LazilyConstructedPhrase.Factory listPhrase() {
        return () -> {
            SingleTokenPhrase leftBracketPhrase = new SingleTokenPhrase(TokenType.LBRACK);
            SingleTokenPhrase rightBracketPhrase = new SingleTokenPhrase(TokenType.RBRACK);
            SingleTokenPhrase namePhrase = new SingleTokenPhrase(TokenType.NAME);

            /** list : '[' elements ']' ; // match bracketed list */
            LazilyConstructedPhrase listPhrase = new LazilyConstructedPhrase(listPhrase());

            /** element : name | list ; // element is name or nested list */
            AlternationPhrase elementPhrase = new AlternationPhrase(iterableWith(namePhrase, listPhrase));

            /** elements : element (',' element)* ; */
            OneOrMorePhrase elementsPhrase = new OneOrMorePhrase(elementPhrase, TokenType.COMMA);

            return new CompositionPhrase(
                    iterableWith(leftBracketPhrase, elementsPhrase, rightBracketPhrase));
        };
    }
}
