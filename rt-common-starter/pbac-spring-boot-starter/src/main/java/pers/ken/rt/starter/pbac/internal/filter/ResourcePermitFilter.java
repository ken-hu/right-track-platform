package pers.ken.rt.starter.pbac.internal.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import pers.ken.rt.starter.pbac.internal.PolicyContext;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;

import java.util.List;

/**
 * @ClassName: ResourcePermitFilter
 * @Created: 2024/6/5
 * @Author ken
 */
@Slf4j
public class ResourcePermitFilter implements PermitFilter {
    @Override
    public boolean matchCheck(PolicyContext context, PolicyDocument.Statement policyStatement) {
        //1 r.userId == p.userId &&
        //2 r.act == p.act &&
        //3 keyMatch2(r.obj,p.obj) && resourceMatch(r.res,p.res)
        List<String> requestResources = context.getRequestResources();
        List<String> policyResources = policyStatement.getResources();
        for (String requestResource : requestResources) {
            // 获取当前请求的资源,记录请求的资源(可能是多条资源),需要全部命中,才允许通过执行下一个Filter
            boolean matched = false;
            for (String policyResource : policyResources) {
                if (rnMatch(requestResource, policyResource)) {
                    matched = true;
                    break;
                }
            }
            // 遍历完了所有policyResource仍然没有任何能命中的，则requestRn不匹配可以提前退出循环
            if (!matched) {
                return false;
            }
        }
        return true;
    }


    private static boolean rnMatch(String requestRn, String policyRn) {
        PathPattern parse = PathPatternParser.defaultInstance.parse(policyRn);
        return parse.matches(PathContainer.parsePath(requestRn));
    }
}
