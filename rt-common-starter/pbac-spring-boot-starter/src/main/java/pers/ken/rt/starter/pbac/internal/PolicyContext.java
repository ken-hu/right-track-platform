package pers.ken.rt.starter.pbac.internal;

import lombok.Getter;

import java.util.List;

/**
 * Creation Time: 2022/11/14 15:27.
 *
 * @author _Ken.Hu
 */
@Getter
public class PolicyContext {
    private PolicyContext(String serviceName, List<PolicyDocument> policies) {
        this.serviceName = serviceName;
        this.policies = policies;
    }

    private final String serviceName;
    private final List<PolicyDocument> policies;

    private String requestAction;
    private List<String> requestResources;


    private PolicyDocument matchPolicyDocument;
    private PolicyDocument.Statement matchStatement;
    private String matchRn;

    public static PolicyContext init(String serviceName, List<PolicyDocument> policies) {
        return new PolicyContext(serviceName, policies);
    }

    public PolicyContext action(String action) {
        this.requestAction = action;
        return this;
    }

    public PolicyContext resources(List<String> resources) {
        this.requestResources = resources;
        return this;
    }

    public void updateMatchRn(String rn) {
        this.matchRn = rn;
    }

    public void updateMatchPolicyDocument(PolicyDocument document, PolicyDocument.Statement statement) {
        this.matchStatement = statement;
        this.matchPolicyDocument = document;
    }
}
