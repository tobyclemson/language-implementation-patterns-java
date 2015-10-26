package com.tobyclemson.lip.standard.pattern5;

import com.tobyclemson.lip.standard.pattern2.Lexer;

public class BacktrackParser extends Parser {
    public BacktrackParser(Lexer lexer) {
        super(lexer);
    }

    /** list : '[' elements ']' ; // match bracketed list */
    public void list() throws RecognitionException {
        match(BacktrackLexer.LBRACK);
        elements();
        match(BacktrackLexer.RBRACK);
    }

    /** assign : list '=' list ; // parallel assignment */
    public void assign() throws RecognitionException {
        list(); match(BacktrackLexer.EQUALS); list();
    }

    /** elements : element (',' element)* ; */
    void elements() throws RecognitionException {
        element();
        while (LA(1) == BacktrackLexer.COMMA) {
            match(BacktrackLexer.COMMA);
            element();
        }
    }

    /** element : NAME '=' NAME | NAME | list ; assignment, NAME or list */
    void element() throws RecognitionException {
        if (LA(1) == BacktrackLexer.NAME && LA(2) == BacktrackLexer.EQUALS) {
            match(BacktrackLexer.NAME);
            match(BacktrackLexer.EQUALS);
            match(BacktrackLexer.NAME);
        } else if (LA(1) == BacktrackLexer.NAME) {
            match(BacktrackLexer.NAME);
        } else if (LA(1) == BacktrackLexer.LBRACK) {
            list();
        } else {
            throw new Error("expecting name or list; found " + LT(1));
        }
    }

    /** stat : list EOF | assign EOF ; */
    public void stat() throws RecognitionException {
        // attempt alternative 1: list EOF
        if (speculate_stat_alt1()) {
            list(); match(Lexer.EOF_TYPE);
        }
        // attempt alternative 2: assign EOF
        else if (speculate_stat_alt2()) {
            assign(); match(Lexer.EOF_TYPE);
        }
        // must be an error; neither matched; LT(1) is lookahead token 1
        else throw new NoViableAltException("expecting stat found" + LT(1));
    }

    private boolean speculate_stat_alt1() {
        boolean success = true;
        mark(); // mark this spot in input so we can rewind
        try {
            list(); match(Lexer.EOF_TYPE);
        } catch (RecognitionException e) {
            success = false;
        }
        release(); // either way, rewind to where we were
        return success;
    }

    private boolean speculate_stat_alt2() {
        boolean success = true;
        mark(); // mark this spot in input so we can rewind
        try {
            assign(); match(Lexer.EOF_TYPE);
        } catch (RecognitionException e) {
            success = false;
        }
        release(); // either way, rewind to where we were
        return success;
    }
}
