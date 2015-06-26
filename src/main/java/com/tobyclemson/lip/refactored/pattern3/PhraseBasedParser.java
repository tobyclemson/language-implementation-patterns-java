package com.tobyclemson.lip.refactored.pattern3;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.pattern2.Token;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhraseBasedParser implements Parser {
    LookaheadBuffer<Token> lookaheadBuffer;
    Phrase phrase;

    @Override public void parse() {
        phrase.apply(lookaheadBuffer);
    }
}
