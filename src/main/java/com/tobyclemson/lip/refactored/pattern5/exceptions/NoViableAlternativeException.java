package com.tobyclemson.lip.refactored.pattern5.exceptions;

import com.tobyclemson.lip.refactored.pattern3.exceptions.RecognitionException;

public class NoViableAlternativeException extends RecognitionException {
    public NoViableAlternativeException(String message) {
        super(message);
    }
}
