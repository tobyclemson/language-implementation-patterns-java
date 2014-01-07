package com.tobyclemson.lip.pattern3;

import com.tobyclemson.lip.pattern2.ListLexer;
import org.junit.Test;

public class Pattern3Test {
    /**
     * list     : '[' elements ']' ;       // match bracketed list
     * elements : element (',' element)* ; // match comma-separated list
     * element  : NAME | list ;            // element is name or nested list
     * NAME     : ('a'..'z'|'A'..'Z')+ ;   // NAME is sequence of >=1 letter
     */
    @Test public void tokenisesListGrammar() {
        String input = "[a, b ]";
        ListLexer lexer = new ListLexer(input);
        ListParser parser = new ListParser(lexer);
        parser.list();
    }
}

