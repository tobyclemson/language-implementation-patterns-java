package com.tobyclemson.lip.refactored.pattern3;

import com.tobyclemson.lip.support.Parsers;
import org.junit.Test;

public class Pattern3Test {
    /**
     * list     : '[' elements ']' ;       // match bracketed list
     * elements : element (',' element)* ; // match comma-separated list
     * element  : NAME | list ;            // element is name or nested list
     * NAME     : ('a'..'z'|'A'..'Z')+ ;   // NAME is sequence of >=1 letter
     */

    @Test public void parsesListOfDepthOneWithoutError() {
        // Given
        String input = "[a, b ]";
        Parser parser = Parsers.listParserFor(input);

        // When
        parser.parse();

        // Then no exceptions are thrown
    }

    @Test public void parsesNestedListsWithoutError() {
        // Given
        String input = "[ant, [bear, [cat]], dog]";
        Parser parser = Parsers.listParserFor(input);

        // When
        parser.parse();

        // Then no exceptions are thrown
    }
}

