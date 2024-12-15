package com.rubinho.lab1.exceptions;

public class TwoPhaseCommitException extends RuntimeException {
    public TwoPhaseCommitException(String message) {
        super(message);
    }
}
