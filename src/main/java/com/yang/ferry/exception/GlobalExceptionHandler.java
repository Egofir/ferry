package com.yang.ferry.exception;

import com.yang.ferry.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception:", e);
        return ApiRestResponse.error(FerryExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(FerryException.class)
    @ResponseBody
    public Object handleFerryException(FerryException e) {
        log.error("FerryException:", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }
}
