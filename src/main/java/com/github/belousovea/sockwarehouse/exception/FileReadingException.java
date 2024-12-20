package com.github.belousovea.sockwarehouse.exception;

public class FileReadingException extends RuntimeException {
    public FileReadingException(String message, Throwable cause) {
        super(String.format("%s with file %s", cause.getMessage(), message), cause);
    }
}
