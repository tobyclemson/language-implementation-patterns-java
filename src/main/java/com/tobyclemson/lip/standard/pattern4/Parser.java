package com.tobyclemson.lip.standard.pattern4;

import com.tobyclemson.lip.standard.pattern2.Lexer;
import com.tobyclemson.lip.standard.pattern2.Token;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

public class Parser {
    private final Lexer input;
    protected Token[] lookahead;
    private int k;
    private int p = 0;

    public Parser(Lexer input, int k) {
        this.input = input;
        this.k = k;
        lookahead = new Token[k];
        for (int i = 1; i <= k; i++) {
            consume();
        }
    }

    public Token LT(int i) {
        return lookahead[(p+i-1) % k]; // circular fetch
    }

    public int LA(int i) {
        return LT(i).type;
    }

    public void match(int x) {
        if (LA(1) == x) {
            consume();
        } else {
            throw new Error("expecting " + input.getTokenName(x) + ", found " + LT(1));
        }
    }

    private void consume() {
        lookahead[p] = input.nextToken();
        p = (p+1) % k;
    }
}
