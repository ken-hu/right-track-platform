package pers.ken.rt.starter.pbac.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.PathContainer;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import pers.ken.rt.starter.pbac.internal.Policy;
import pers.ken.rt.starter.pbac.internal.Rn;
import pers.ken.rt.starter.pbac.internal.Statement;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <code> PolicyResolver </code>
 * <desc> PolicyResolver </desc>
 * <b>Creation Time:</b> 2022/8/2 16:16.
 *
 * @author Ken.Hu
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class PolicyResolver {
    public static boolean isPermit(List<Policy> policies, List<String> requestedResources, String requestedAction) {
        //1 r.userId == p.userId &&
        //2 r.act == p.act &&
        //3 keyMatch2(r.obj,p.obj) && resourceMatch(r.res,p.res)
        for (Policy policy : policies) {
            List<Statement> statements = policy.getStatements();
            for (Statement statement : statements) {
                List<String> actions = statement.getActions();
                for (String action : actions) {
                    if (actionMatch(action, requestedAction)) {
                        List<String> ownResources = statement.getResources();
                        if (statement.getEffect().equals(Statement.Effect.Allow)) {
                            // own resource
                            return resourceMatch(requestedResources, ownResources);
                        } else if (statement.getEffect().equals(Statement.Effect.Deny)) {
                            return !resourceMatch(requestedResources, ownResources);
                        } else {
                            //Ignore
                        }
                    }
                }
            }
        }
        return Boolean.FALSE;
    }

    private static boolean resourceMatch(List<String> requestedResources, List<String> ownResources) {
        return requestedResources
                .stream()
                .allMatch(requestedResource ->
                        ownResources.stream()
                                .anyMatch(ownResource ->
                                        resourceMatch(ownResource, requestedResource))
                );
    }

    public static List<String> getPolicyResources(List<Policy> policies) {
        return policies.stream()
                .map(policy -> policy.getStatements().stream()
                        .filter(s -> !CollectionUtils.isEmpty(s.getResources()))
                        .flatMap(x -> x.getResources().stream())
                        .collect(Collectors.toList())
                ).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static Set<String> getPolicyOwnResources(List<Policy> policies, String resourceId) {
        return getPolicyResources(policies)
                .stream()
                .filter(r -> {
                    try {
                        Rn rn = Rn.fromString(r);
                        return resourceId.equals(rn.getRnResource().getResourceType());
                    } catch (Exception e) {
                        log.error("Rn of failed", e);
                    }
                    return Boolean.FALSE;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static boolean actionMatch(String ownAction, String currentAction) {
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
}
