package com.tobyclemson.lip.refactored.pattern3.exceptions;

public class MismatchedTokenException extends RecognitionException {
    public MismatchedTokenException(String message) {
        super(message);
    }
}
