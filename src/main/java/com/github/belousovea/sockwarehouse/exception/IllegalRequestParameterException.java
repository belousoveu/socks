package com.github.belousovea.sockwarehouse.exception;

public class IllegalRequestParameterException extends RuntimeException {
    public IllegalRequestParameterException(String parameterName, Object parameterValue) {
        super(String.format("Некорректное значение параметра '%s': %s", parameterName, parameterValue.toString()));
    }
}
