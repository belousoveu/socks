package com.github.belousovea.sockwarehouse.exception;

public class IllegalFilterRangeException extends RuntimeException {
    public IllegalFilterRangeException(int  min, int max) {
        super("Недопустимые значения минимального и максимального интервала: " + min + " и " + max);
    }
}
