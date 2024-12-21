package com.github.belousovea.sockwarehouse.exception;

public class TooManySortingParametersException extends RuntimeException {
    public TooManySortingParametersException() {
        super("Передано слишком много параметров сортировки");

    }
}
