package com.tobyclemson.lip.standard.pattern5;

import org.junit.Test;

public class Pattern5Test {
    /**
     * stat     : list EOF | assign EOF ;
     * assign   : list '=' list ;
     * list     : '[' elements ']' ;            // match bracketed list
     * elements : element (',' element)* ;      // match comma-separated list
     * element  : NAME '=' NAME | NAME | list ; //element is name, nested list
     */
    @Test public void parsesCorrectly() throws RecognitionException {
        String input = "[a,b,c]=[d,e,f]";
        BacktrackLexer lexer = new BacktrackLexer(input);
        BacktrackParser parser = new BacktrackParser(lexer);
        parser.stat();
    }
}
