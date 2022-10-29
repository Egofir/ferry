package com.yang.ferry.exception;

public class FerryException extends Exception {
    private final Integer code;
    private final String message;

    public FerryException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public FerryException(FerryExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
