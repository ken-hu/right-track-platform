package pers.ken.rt.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.ken.rt.common.cons.HttpHeaderCons;
import pers.ken.rt.common.model.PlatformError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

import static pers.ken.rt.common.exception.ServiceCode.FAILED;
import static pers.ken.rt.common.exception.ServiceCode.INVALID_REQ;

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
                new PlatformError(INVALID_REQ.getCode(),
                        INVALID_REQ.getMessage(),
                        e.getMessage(),
                        request.getRequestURI(),
                        request.getHeader(HttpHeaderCons.REQUEST_ID)),
                HttpStatus.OK);
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
                new PlatformError(INVALID_REQ.
                        getCode(), INVALID_REQ.
                        getMessage(), detail,
                        request.getRequestURI(),
                        request.getHeader(HttpHeaderCons.REQUEST_ID)),
                HttpStatus.OK);
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<PlatformError> handleBindException(BindException ex, HttpServletRequest request) {
        log.warn("Catch BindException, uri:{}, caused by: ", request.getRequestURI(), ex);
        String detail = validErrorMsgDetail(ex.getBindingResult());
        return new ResponseEntity<>(
                new PlatformError(INVALID_REQ.
                        getCode(), INVALID_REQ.
                        getMessage(), detail,
                        request.getRequestURI(),
                        request.getHeader(HttpHeaderCons.REQUEST_ID)),
                HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PlatformError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                               HttpServletRequest request) {
        log.warn("Catch MethodArgumentNotValidException, caused by: ", ex);
        String detail = validErrorMsgDetail(ex.getBindingResult());
        return new ResponseEntity<>(
                new PlatformError(INVALID_REQ.
                        getCode(), INVALID_REQ.
                        getMessage(), detail,
                        request.getRequestURI(),
                        request.getHeader(HttpHeaderCons.REQUEST_ID)),
                HttpStatus.OK);
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
    public ResponseEntity<PlatformError> serviceException(ServiceException ex, HttpServletRequest request) {
        log.error("Catch Service Exception caused by: ", ex);
        return new ResponseEntity<>(
                new PlatformError(ex.getCode(),
                        ex.getMsg(),
                        ex.getDetail(),
                        request.getRequestURI(),
                        request.getHeader(HttpHeaderCons.REQUEST_ID)),
                HttpStatus.OK);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<PlatformError> unknownException(Exception ex, HttpServletRequest request) {
        log.error("Catch Unknown Exception, caused by: ", ex);
        return new ResponseEntity<>(
                new PlatformError(FAILED.getCode(),
                        FAILED.getMessage(),
                        FAILED.getMessage(),
                        request.getRequestURI(),
                        request.getHeader(HttpHeaderCons.REQUEST_ID)),
                HttpStatus.OK);
    }
}
