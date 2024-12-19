package com.github.belousovea.sockwarehouse.exception;

public class IllegalFilterParametersException extends RuntimeException {
    public IllegalFilterParametersException() {

        super("Указаны неверные параметры фильтрации");
    }
}
