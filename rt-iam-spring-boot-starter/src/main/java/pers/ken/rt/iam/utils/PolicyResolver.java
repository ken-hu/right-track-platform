package pers.ken.rt.iam.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import pers.ken.rt.iam.internal.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code> PolicyResolver </code>
 * <desc> PolicyResolver </desc>
 * <b>Creation Time:</b> 2022/8/2 16:16.
 *
 * @author Ken.Hu
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PolicyResolver {
    public static boolean isPermit(List<Policy> policies, String currentResource, String currentAction) {
        //1 r.userId == p.userId &&
        //2 r.act == p.act &&
        //3 keyMatch2(r.obj,p.obj)
        for (Policy policy : policies) {
            List<Statement> statements = policy.getStatement();
            for (Statement statement : statements) {
                List<String> actions = statement.getAction();
                for (String action : actions) {
                    if (statement.getEffect().equals(Statement.Effect.Allow) && actionMatch(action, currentAction)) {
                        List<String> resources = statement.getResource();
                        for (String resource : resources) {
                            if (resourceMatch(resource, currentResource)) {
                                return Boolean.TRUE;
                            }
                        }
                    }
                }
            }
        }
        return Boolean.FALSE;
    }
    public static boolean actionMatch(String ownAction,String currentAction){
        if (ownAction.equalsIgnoreCase(currentAction)) {
            return true;
        }
        PathPattern parse = PathPatternParser.defaultInstance.parse(ownAction);
        return parse.matches(PathContainer.parsePath(currentAction));
    }

    public static boolean resourceMatch(String ownResource, String accessResource) {
        PathPattern parse = PathPatternParser.defaultInstance.parse(ownResource);
        return parse.matches(PathContainer.parsePath(accessResource));
    }

    public static <T extends AbstractResource> T resolveResource(String resource, ResourceConvert<T> convert) {
        Rn rn = Rn.fromString(resource);
        return convert.convert(rn);
    }

    public static <T extends AbstractResource> List<T> resolveResource(List<Policy> policies, ResourceConvert<T> convert) {
        return policies.stream()
                .map(policy -> policy.getStatement()
                        .stream()
                        .filter(statement -> statement.getEffect().equals(Statement.Effect.Allow))
                        .flatMap(statement -> statement.getResource().stream())
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .map(resource -> resolveResource(resource, convert))
                .collect(Collectors.toList());
    }
}
