package pers.ken.rt.auth.domain.entity.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import pers.ken.rt.auth.domain.entity.PolicyDocument;
import pers.ken.rt.auth.domain.entity.valueobj.Statement;

import java.util.List;

/**
 * @author Ken
 * @className: Policy
 * @createdTime: 2023/3/9 16:38
 * @desc:
 */
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Policy {
    private Long id;
    private String name;
    private String version;
    private PolicyDocument policyDocument;

    public Policy(String name, String version, PolicyDocument policyDocument) {
        this.name = name;
        this.version = version;
        this.policyDocument = policyDocument;
    }

    public boolean check(List<String> requestedResources, String requestedAction) {
        for (Statement statement : this.policyDocument.getStatements()) {
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
        return false;
    }

    private static boolean actionMatch(String ownAction, String currentAction) {
        if (ownAction.equalsIgnoreCase(currentAction)) {
            return true;
        }
        PathPattern parse = PathPatternParser.defaultInstance.parse(ownAction);
        return parse.matches(PathContainer.parsePath(currentAction));
    }

    private static boolean resourceMatch(String ownResource, String accessResource) {
        PathPattern parse = PathPatternParser.defaultInstance.parse(ownResource);
        return parse.matches(PathContainer.parsePath(accessResource));
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
}
