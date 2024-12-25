package pers.ken.rt.common.web;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import pers.ken.rt.common.exception.ServiceException;
import pers.ken.rt.common.model.ErrorResponse;

import java.util.Objects;
import java.util.stream.Collectors;

import static pers.ken.rt.common.exception.ErrorCode.*;

/**
 * <name> GlobalExceptionHandler </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 17:15.
 *
 * @author _Ken.Hu
 */
@Slf4j
@RestControllerAdvice
@Order(100)
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpMethodNotSupportException(HttpRequestMethodNotSupportedException e) {
        ErrorResponse errorResponse = ErrorResponse.of(INVALID_PARAMETERS, e.getMessage());
        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> noHandlerFoundExceptionException(NoHandlerFoundException e) {
        log.error("Cache NoHandlerFoundException.", e);
        ErrorResponse errorResponse = ErrorResponse.of(RESOURCE_NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("Catch HttpMessageNotReadableException. Caused by: ", e);
        ErrorResponse errorResponse = ErrorResponse.of(INVALID_PARAMETERS, e.getMessage());
        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("Catch ConstraintViolationException. Caused by: ", ex);
        String detail = ex
                .getConstraintViolations()
                .iterator()
                .next()
                .getMessage();
        ErrorResponse errorResponse = ErrorResponse.of(INVALID_PARAMETERS, detail);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        log.warn("Catch BindException. Caused by: ", ex);
        String detail = validErrorMsgDetail(ex.getBindingResult());
        ErrorResponse errorResponse = ErrorResponse.of(INVALID_PARAMETERS, detail);
        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Catch MethodArgumentNotValidException, caused by: ", ex);
        String detail = validErrorMsgDetail(ex.getBindingResult());
        ErrorResponse errorResponse = ErrorResponse.of(INVALID_PARAMETERS, detail);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST);
    }

    private String validErrorMsgDetail(BindingResult bindingResult) {
        if (Objects.isNull(bindingResult)) {
            return null;
        }
        return bindingResult.getFieldErrors()
                .stream()
                .map(error -> {
                    String field = error.getField();
                    String msg = error.getDefaultMessage();
                    return String.format("[%s:%s]", field, msg);
                })
                .collect(Collectors.joining(";"));
    }


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> serviceException(ServiceException ex) {
        log.error("Catch Service Exception caused by: ", ex);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = ErrorResponse.of(ex.getErrorCode(), ex.getMessage());
        String code = ex.getErrorCode().getCode();
        if (Objects.equals(AUTHENTICATION_FAILED.getCode(), code)) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        if (Objects.equals(ACCESS_DENY.getCode(), code)) {
            httpStatus = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(
                errorResponse,
                httpStatus);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unknownException(Exception ex) {
        log.error("Catch Unknown Exception. Caused by: ", ex);
        ErrorResponse errorResponse = ErrorResponse.of(FAILED, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
