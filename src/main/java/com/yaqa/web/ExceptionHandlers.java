package com.yaqa.web;

import com.yaqa.exception.NotFoundException;
import com.yaqa.exception.YaqaBaseException;
import com.yaqa.web.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlers {

    @ExceptionHandler(YaqaBaseException.class)
    public ResponseEntity<ErrorResponse> notFoundException(YaqaBaseException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> unexpectedError(Throwable t) {
        return new ResponseEntity<>("unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
