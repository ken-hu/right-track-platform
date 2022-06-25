package pers.ken.rt.uaa.oauth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * <name> CustomerWebResponseExceptionTranslator </name>
 * <desc> CustomerWebResponseExceptionTranslator </desc>
 * Creation Time: 2021/10/7 21:05.
 *
 * @author _Ken.Hu
 */
@Slf4j
@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        log.error("Catch OAuth2Exception", e);
        if (e instanceof OAuth2Exception) {
            return new ResponseEntity<>((OAuth2Exception) e, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new OAuth2Exception(e.getMessage(), e), HttpStatus.UNAUTHORIZED);
    }
}
