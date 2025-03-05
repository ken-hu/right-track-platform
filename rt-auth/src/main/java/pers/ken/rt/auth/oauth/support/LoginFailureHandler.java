package pers.ken.rt.auth.oauth.support;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pers.ken.rt.auth.dto.resp.LoginFailureResponse;
import pers.ken.rt.common.utils.Jackson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: LoginFailureHandler
 * @Created: 2024/11/20 21:38
 * @Author ken
 */
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private static final Map<String, AtomicInteger> LOGIN_FAILED_COUNT_MAP = new ConcurrentHashMap<>();

    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // todo Use redis cache
        String username = request.getParameter("username");
        int recordLoginCount = recordLoginCount(username);
        if (recordLoginCount >= 1) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(Jackson.toJsonString(new LoginFailureResponse("登录次数超了！" + recordLoginCount)));
            response.getWriter().flush();
            return;
        }
        log.warn("LoginFailure:{}", exception.getMessage());
        // 登录失败，写回401与具体的异常
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(Jackson.toJsonString(new LoginFailureResponse(exception.getMessage())));
        response.getWriter().flush();

    }


    private int recordLoginCount(String username) {
        AtomicInteger atomicInteger = LOGIN_FAILED_COUNT_MAP.get(username);
        if (null != atomicInteger) {
            int count = atomicInteger.incrementAndGet();
            log.warn("user:{} login failed count:{}", username, count);
            return count;
        } else {
            LOGIN_FAILED_COUNT_MAP.put(username, new AtomicInteger(0));
            return 0;
        }
    }

}
