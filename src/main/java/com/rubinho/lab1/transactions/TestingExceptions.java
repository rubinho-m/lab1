package com.rubinho.lab1.transactions;

public record TestingExceptions(boolean dbException, boolean s3Exception, boolean runtimeException) {
}
