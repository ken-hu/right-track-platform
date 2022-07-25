package pers.ken.rt.uaa.common;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.ken.rt.common.exception.ServiceCode;
import pers.ken.rt.common.iam.IAMAuth;
import pers.ken.rt.common.iam.IAMAuthContext;
import pers.ken.rt.common.iam.UserDetail;
import pers.ken.rt.uaa.iam.CasbinUtils;
import pers.ken.rt.uaa.iam.exception.IAMAuthException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code> AuthorityInterceptor </code>
 * <desc> AuthorityInterceptor </desc>
 * <b>Creation Time:</b> 2022/3/8 22:16.
 *
 * @author _Ken.Hu
 */
@Component
public class IAMAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //Get userInfo
        String userId = "";
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            IAMAuth iamAuth = handlerMethod.getMethodAnnotation(IAMAuth.class);
            if (null == iamAuth) {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }

            // API access control
            if (!CasbinUtils.validate(userId, iamAuth.resource(), iamAuth.action())) {
                throw new IAMAuthException(ServiceCode.AUTHENTICATION_FAILED);
            }
            Enforcer enforcer = CasbinUtils.enforcerCreate();

            List<List<String>> userPolicies = enforcer.getFilteredPolicy(0, userId, "", iamAuth.action());
            List<UserDetail.PolicyDefine> policies = userPolicies.stream().map(UserDetail.PolicyDefine::new).collect(Collectors.toList());
            IAMAuthContext.set(UserDetail.builder()
                    .userId(userId)
                    .username(userId)
                    .policies(policies)
                    .build());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        IAMAuthContext.remove();
    }
}
