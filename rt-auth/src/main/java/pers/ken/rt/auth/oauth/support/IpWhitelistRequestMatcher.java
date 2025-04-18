package pers.ken.rt.auth.oauth.support;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;

/**
 * @ClassName: IpWhitelistFilter
 * @Created: 2025/4/3 14:39
 * @Author ken
 */
@Slf4j
public class IpWhitelistRequestMatcher implements RequestMatcher {
    private final List<String> allowedIps = List.of("127.0.0.1");
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final String pattern;

    public IpWhitelistRequestMatcher(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 获取客户端IP的方法,需要完善.. 或者应用外部的成熟工具类
     * 代理IP的情况需要测试，
     *
     * @param request
     * @return
     */
    private static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级代理时取第一个 IP
        return ip.split(",")[0].trim();
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        // 1. 校验路径是否匹配
        boolean pathMatches = pathMatcher.match(pattern, request.getServletPath());
        if (!pathMatches) {
            return false;
        }
        String clientIp = getClientIp(request);
        log.info("{} is AllowedIp. Request path {} Allowed pattern{}. Secure", clientIp, request, this.pattern);
        return allowedIps.contains(clientIp);
    }
}
