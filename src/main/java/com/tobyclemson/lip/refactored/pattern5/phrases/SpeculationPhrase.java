package com.tobyclemson.lip.refactored.pattern5.phrases;

import com.tobyclemson.lip.refactored.common.LookaheadBuffer;
import com.tobyclemson.lip.refactored.common.Phrase;
import com.tobyclemson.lip.refactored.common.Token;
import com.tobyclemson.lip.refactored.pattern3.exceptions.RecognitionException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpeculationPhrase implements Phrase {
    Phrase delegate;

    @Override
    public boolean isSelectedBy(LookaheadBuffer<Token> lookaheadBuffer) {
        boolean success = true;
        lookaheadBuffer.mark();
        try {
            delegate.apply(lookaheadBuffer);
        } catch (RecognitionException e) {
            success = false;
        }
        lookaheadBuffer.release();
        return success;
    }

    @Override
    public void apply(LookaheadBuffer<Token> lookaheadBuffer) {
        delegate.apply(lookaheadBuffer);
    }
}
