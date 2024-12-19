package com.github.belousovea.sockwarehouse.exception;

public class TooManyFilterParametersException extends RuntimeException {
    public TooManyFilterParametersException() {

        super("Переданы взаимоисключающие параметры для фильтрации");
    }
}
