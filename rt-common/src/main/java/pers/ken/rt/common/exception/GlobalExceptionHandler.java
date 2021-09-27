package pers.ken.rt.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.ken.rt.common.model.PlatformError;
import pers.ken.rt.common.model.PlatformResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

import static pers.ken.rt.common.exception.ServiceCode.*;

/**
 * <name> GlobalExceptionHandler </name>
 * <desc> </desc>
 * Creation Time: 2021/9/19 17:15.
 *
 * @author _Ken.Hu
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<PlatformError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.warn("Catch HttpMessageNotReadableException,  caused by: ", e);
        return new ResponseEntity<>(
                new PlatformError(INVALID_REQ, request.getRequestURI(), e.getMessage()),
                INVALID_REQ.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<PlatformError> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.warn("Catch ConstraintViolationException, caused by: ", ex);
        String detail = ex
                .getConstraintViolations()
                .iterator()
                .next()
                .getMessage();
        return new ResponseEntity<>(
                new PlatformError(INVALID_REQ, request.getRequestURI(), detail),
                INVALID_REQ.getHttpStatus());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<PlatformError> handleBindException(BindException ex, HttpServletRequest request) {
        log.warn("Catch BindException, uri:{}, caused by: ", request.getRequestURI(), ex);
        String detail = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return new ResponseEntity<>(
                new PlatformError(INVALID_REQ, request.getRequestURI(), detail),
                INVALID_REQ.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PlatformError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                               HttpServletRequest request) {
        log.warn("Catch MethodArgumentNotValidException, caused by: ", ex);
        String detail = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return new ResponseEntity<>(
                new PlatformError(INVALID_REQ, request.getRequestURI(), detail),
                INVALID_REQ.getHttpStatus());
    }


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<PlatformError> serviceException(ServiceException ex, HttpServletRequest request) {
        log.error("Catch Service Exception caused by: ", ex);
        String uri = request.getRequestURI();
        return new ResponseEntity<>(
                new PlatformError(ex.getServiceCode(), uri, ex.getMessage()),
                ex.serviceCode.getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<PlatformError> unknownException(Exception ex, HttpServletRequest request) {
        log.error("Catch Unknown Exception, caused by: ", ex);
        return new ResponseEntity<>(
                new PlatformError(FAILED, request.getRequestURI(), ex.getMessage()),
                FAILED.getHttpStatus());
    }
}
