package pers.ken.rt.auth.service.impl;

import org.springframework.stereotype.Service;
import pers.ken.rt.auth.entity.Policy;
import pers.ken.rt.auth.repository.PolicyRepository;
import pers.ken.rt.auth.service.PolicyService;

import java.util.List;

/**
 * <code> PolicyDocumentServiceImpl </code>
 * <desc> PolicyDocumentServiceImpl </desc>
 * <b>Creation Time:</b> 2022/6/14 15:14.
 *
 * @author Ken.Hu
 */
@Service
public class PolicyServiceImpl implements PolicyService {
    private PolicyRepository policyRepository;

    @Override
    public List<Policy> userPolicies(String userId) {
        return null;
    }
}
