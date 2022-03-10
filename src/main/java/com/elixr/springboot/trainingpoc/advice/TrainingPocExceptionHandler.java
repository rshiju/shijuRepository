package com.elixr.springboot.trainingpoc.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.elixr.springboot.trainingpoc.dto.ErrorResponse;


@ControllerAdvice
public class TrainingPocExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TrainingPocException.class)
    protected ResponseEntity<ErrorResponse> handleEmptyInput(TrainingPocException ex) {
		ErrorResponse errorResponse = new ErrorResponse(false, ex.getErrorMessage(ex.getErrorCode()));
		return new ResponseEntity<>(errorResponse, ex.getHttpStatusCode(ex.getErrorCode()));
    }
}
