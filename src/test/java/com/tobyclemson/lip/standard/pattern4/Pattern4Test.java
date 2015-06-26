package com.tobyclemson.lip.standard.pattern4;

import com.tobyclemson.lip.standard.pattern2.ListLexer;
import com.tobyclemson.lip.standard.pattern3.ListParser;
import org.junit.Test;

public class Pattern4Test {
    /**
     * list     : '[' elements ']' ;            // match bracketed list
     * elements : element (',' element)* ;      // match comma-separated list
     * element  : NAME '=' NAME | NAME | list ; // element is assignment, name or nested list
     * NAME     : ('a'..'z'|'A'..'Z')+ ;        // NAME is sequence of >=1 letter
     */
    @Test public void tokenisesListGrammar() {
        String input = "[a,b=c,[d,e]]";
        LookaheadLexer lexer = new LookaheadLexer(input);
        LookaheadParser parser = new LookaheadParser(lexer, 2);
        parser.list();
    }
}
