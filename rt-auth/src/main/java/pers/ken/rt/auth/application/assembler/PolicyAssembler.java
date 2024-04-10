package pers.ken.rt.auth.application.assembler;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.domain.entity.PolicyDocument;
import pers.ken.rt.auth.domain.entity.aggregate.Policy;
import pers.ken.rt.auth.interfaces.dto.req.PolicySaveRequest;
import pers.ken.rt.auth.interfaces.dto.resp.PolicyItemResp;
import pers.ken.rt.auth.interfaces.dto.resp.PolicyResp;

import java.util.List;

/**
 * The interface Policy convert.
 *
 * @ClassName: PolicyConvert
 * @CreatedTime: 2023 /1/9 15:25
 * @Desc:
 * @Author Ken
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PolicyAssembler {

    /**
     * The constant INSTANCE.
     */
    PolicyAssembler INSTANCE = Mappers.getMapper(PolicyAssembler.class);

    /**
     * Convert policy resp.
     *
     * @param policy the policy
     * @return the policy resp
     */
    @Mappings(value = {
            @Mapping(source = "version", target = "versionId")}
    )
    PolicyResp convert(Policy policy);

    /**
     * Convert list.
     *
     * @param policies the policies
     * @return the list
     */
    List<PolicyResp> convert(List<Policy> policies);

    /**
     * Convert policy.
     *
     * @param saveRequest the save request
     * @return the policy
     */
    @Mapping(source = "policyDocument", target = "policyDocument", qualifiedByName = "createPolicyFromJson")
    Policy convert(PolicySaveRequest saveRequest);

    /**
     * To policy item list.
     *
     * @param policies the policies
     * @return the list
     */
    default List<PolicyItemResp> toPolicyItem(List<Policy> policies) {
        return policies.stream()
                .map(policy -> PolicyItemResp.builder()
                        .id(policy.getId())
                        .versionId(policy.getVersion())
                        .name(policy.getName())
                        .build())
                .toList();
    }

    /**
     * Create policy from json policy document.
     *
     * @param policyDocument the policy document
     * @return the policy document
     */
    @Named("createPolicyFromJson")
    default PolicyDocument createPolicyFromJson(String policyDocument) {
        return PolicyDocument.createFromJson(policyDocument);
    }
}
