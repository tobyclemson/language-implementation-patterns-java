package com.tobyclemson.lip.standard.pattern3;

import com.tobyclemson.lip.standard.pattern2.Lexer;
import com.tobyclemson.lip.standard.pattern2.Token;

public class Parser {
    private Lexer input;       // from where do we get tokens?
    protected Token lookahead; // the current lookahead token

    public Parser(Lexer input) {
        this.input = input;
        lookahead = input.nextToken();
    }

    /** If lookahead token type matches x, consume and return else error */
    public void match(int x) {
        if (lookahead.type == x) {
            consume();
        } else {
            throw new Error(
                    "expecting " + input.getTokenName(x) +
                            "; found " + lookahead);
        }
    }

    public void consume() {
        lookahead = input.nextToken();
    }
}
