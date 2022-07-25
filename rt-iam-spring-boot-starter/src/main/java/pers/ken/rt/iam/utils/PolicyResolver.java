package pers.ken.rt.iam.utils;

import org.springframework.util.AntPathMatcher;
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
public class PolicyResolver {
    private static final AntPathMatcher RESOURCE_MATCHER = new AntPathMatcher();

    public static boolean isPermit(List<Policy> policies, String currentResource, String currentAction) {
        //1 r.userId == p.userId &&
        //2 r.act == p.act &&
        //3 keyMatch2(r.obj,p.obj)
        for (Policy policy : policies) {
            List<Statement> statements = policy.getStatements();
            for (Statement statement : statements) {
                List<String> actions = statement.getActions();
                for (String action : actions) {
                    if (statement.getEffect().equals(Statement.Effect.Allow) && action.equalsIgnoreCase(currentAction)) {
                        List<String> resources = statement.getResources();
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

    public static boolean resourceMatch(String ownResource, String accessResource) {
        return RESOURCE_MATCHER.match(ownResource, accessResource);
    }

    public static <T extends AbstractResource> T resolveResource(String resource, ResourceConvert<T> convert) {
        Rn rn = Rn.fromString(resource);
        if (!rn.getResourceType().equals(convert.resourceId())) {
            throw new ResourceResolveException("Convert failed because resourceType: " + rn.getResourceType() + "!=" + convert.resourceId());
        }
        return convert.convert(rn);
    }

    public static <T extends AbstractResource> List<T> resolveResource(List<Policy> policies, ResourceConvert<T> convert) {
        return policies.stream()
                .map(policy -> policy.getStatements()
                        .stream()
                        .filter(statement -> statement.getEffect().equals(Statement.Effect.Allow))
                        .flatMap(statement -> statement.getResources().stream())
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .map(resource -> resolveResource(resource, convert))
                .collect(Collectors.toList());
    }
}
