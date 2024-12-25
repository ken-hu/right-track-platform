package pers.ken.rt.starter.pbac.permission.api;

import pers.ken.rt.starter.pbac.core.PolicyProvider;
import pers.ken.rt.starter.pbac.internal.PolicyDocument;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: InMemoryPolicyProvider
 * @Created: 2023/10/31 18:08
 * @Desc:
 * @Author Ken
 */
public class InMemoryPolicyProvider implements PolicyProvider {

    public static final List<PolicyDocument> POLICY_DOCUMENTS = new ArrayList<>();

    @Override
    public List<PolicyDocument> loadMyPolicies() {
        return POLICY_DOCUMENTS;
    }

    public void addPolicy(PolicyDocument policyDocument) {
        POLICY_DOCUMENTS.add(policyDocument);
    }

    public void addPolicies(List<PolicyDocument> policyDocuments) {
        POLICY_DOCUMENTS.addAll(policyDocuments);
    }
}
