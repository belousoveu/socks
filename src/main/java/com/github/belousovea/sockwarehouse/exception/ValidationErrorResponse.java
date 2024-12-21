package com.github.belousovea.sockwarehouse.exception;


import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {

    public record Violation(String fieldName, String message) {

    }
}