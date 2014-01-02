package com.tobyclemson.lip.pattern2;

import org.junit.Test;

public class Pattern2Test {
    /**
     * list     : '[' elements ']' ;       // match bracketed list
     * elements : element (',' element)* ; // match comma-separated list
     * element  : NAME | list ;            // element is name or nested list
     * NAME     : ('a'..'z'|'A'..'Z')+ ;   // NAME is sequence of >=1 letter
     */
    @Test public void tokenisesListGrammar() {
        String input = "[a, b ]";
        ListLexer lexer = new ListLexer(input);
        Token token = lexer.nextToken();
        while (token.type != Lexer.EOF_TYPE) {
            System.out.println(token);
            token = lexer.nextToken();
        }
        System.out.println(token);
    }

}
