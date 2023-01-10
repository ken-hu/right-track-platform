package pers.ken.rt.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.dto.resp.PolicyResp;
import pers.ken.rt.auth.entity.Policy;

import java.util.List;

/**
 * The interface Policy convert.
 *
 * @ClassName: PolicyConvert
 * @CreatedTime: 2023 /1/9 15:25
 * @Desc:
 * @Author Ken
 */
@Mapper
public interface PolicyConvert {

    PolicyConvert INSTANCE = Mappers.getMapper(PolicyConvert.class);

    /**
     * Convert policy resp.
     *
     * @param policy the policy
     * @return the policy resp
     */
    @Mapping(target = "policyDocument",
            expression = "java(pers.ken.rt.common.utils.Jackson.jsonNodeParseToObject(policy.getPolicyDocument(),pers.ken.rt.auth.oauth.PolicyDocument.class))")
    PolicyResp convert(Policy policy);

    /**
     * Convert list.
     *
     * @param policies the policies
     * @return the list
     */
    List<PolicyResp> convert(List<Policy> policies);
}
