package pers.ken.rt.map.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.ken.rt.common.model.ErrorResponse;
import pers.ken.rt.starter.pbac.exception.BaseAccessManagerException;

import static pers.ken.rt.common.exception.ErrorCode.ACCESS_DENY;

/**
 * The type Map exception handler.
 *
 * @ClassName: MapExceptionHandler
 * @Created: 2024 /11/4 14:19
 * @Author ken
 */
@Slf4j
@RestControllerAdvice
@Order(1)
public class MapExceptionHandler {
    /**
     * Access control exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(BaseAccessManagerException.class)
    public ResponseEntity<ErrorResponse> accessControlException(BaseAccessManagerException e) {
        ErrorResponse errorResponse = ErrorResponse.of(ACCESS_DENY, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
