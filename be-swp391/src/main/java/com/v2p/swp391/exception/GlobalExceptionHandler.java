package com.v2p.swp391.exception;


import com.v2p.swp391.common.api.CoreApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public CoreApiResponse<?> handleBindException(BindException e) {
        List<String> errorMessages = new ArrayList<>();
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                String errorMessage = fieldError.getDefaultMessage();
                errorMessages.add(errorMessage);
            }
        }
        return CoreApiResponse.error(HttpStatus.BAD_REQUEST, "Request không hợp lệ", errorMessages);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CoreApiResponse<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String errorMessage = "Invalid request format";
        return CoreApiResponse.error(HttpStatus.BAD_REQUEST,errorMessage);
    }

    @ExceptionHandler({Exception.class,AppException.class, ResourceNotFoundException.class})
    public CoreApiResponse<?> handleCoreException(Exception error) {
        if(error instanceof AppException){
            AppException appException =(AppException) error;
            return CoreApiResponse.error(HttpStatus.valueOf(appException.getCode()), appException.getMessage(),appException.getData());
        }else if(error instanceof ResourceNotFoundException){
            ResourceNotFoundException resourceException = (ResourceNotFoundException) error;
            return CoreApiResponse.error(HttpStatus.NOT_FOUND, resourceException.getMessage());
        }
        log.error("An error occurred: " + error.getMessage(), error);
        return CoreApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
