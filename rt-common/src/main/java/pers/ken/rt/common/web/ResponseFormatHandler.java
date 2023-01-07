package pers.ken.rt.common.web;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pers.ken.rt.common.model.PlatformResult;
import pers.ken.rt.common.utils.Jackson;

import java.util.Objects;

/**
 * <code> ResponseFormatHandler </code>
 * <desc> ResponseFormatHandler </desc>
 * <b>Creation Time:</b> 2022/2/26 0:14.
 *
 * @author _Ken.Hu
 */
@RestControllerAdvice
public class ResponseFormatHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return Boolean.TRUE;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        FormatIgnore methodFormatIgnore = methodParameter.getMethodAnnotation(FormatIgnore.class);

        FormatIgnore classFormatIgnore = methodParameter.getDeclaringClass().getAnnotation(FormatIgnore.class);
        if (Objects.nonNull(methodFormatIgnore) || Objects.nonNull(classFormatIgnore)) {
            return body;
        }
        return this.getResultByAnnotation(body);
    }

    private Object getResultByAnnotation(Object body) {
        if (body instanceof PlatformResult) {
            return body;
        }
        if (body instanceof String) {
            return Jackson.toJsonString(PlatformResult.ok(body));
        }
        return PlatformResult.ok(body);
    }
}
