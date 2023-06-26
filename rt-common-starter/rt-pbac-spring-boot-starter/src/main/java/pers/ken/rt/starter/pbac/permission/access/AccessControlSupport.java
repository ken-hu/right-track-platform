package pers.ken.rt.starter.pbac.permission.access;

import lombok.AllArgsConstructor;
import pers.ken.rt.starter.pbac.AccessControlProperties;
import pers.ken.rt.starter.pbac.internal.Policy;
import pers.ken.rt.starter.pbac.internal.PolicyGetHandler;
import pers.ken.rt.starter.pbac.utils.PolicyResolver;

import java.util.List;

/**
 * <code> AccessManagementSupport </code>
 * <desc> AccessManagementSupport </desc>
 * <b>Creation Time:</b> 2022/8/22 16:47.
 *
 * @author Ken.Hu
 */
@AllArgsConstructor
public class AccessControlSupport {
    private PolicyGetHandler policyGetHandler;
    private AccessControlProperties accessControlProperties;

    /**
     * User policies list.
     *
     * @return the list
     */
    public List<Policy> userPolicies() {
        return policyGetHandler.loadPolicies();
    }

    public String getRequestedResource(String resourceId, String resource) {
        return String.format("%s:%s:%s/%s",
                accessControlProperties.getServiceName(),
                accessControlProperties.getResourceCode(),
                resourceId, resource
        );
    }


    public boolean isPermit(List<Policy> policies, List<String> requestedResources, String requestedAction) {
        return PolicyResolver.isPermit(policies, requestedResources, requestedAction);
    }
}
