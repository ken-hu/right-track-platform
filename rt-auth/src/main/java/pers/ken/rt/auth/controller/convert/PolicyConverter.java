package pers.ken.rt.auth.controller.convert;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.controller.resp.PolicyDetailResp;
import pers.ken.rt.auth.controller.resp.PolicyListResp;
import pers.ken.rt.auth.repository.po.Policy;
import pers.ken.rt.common.utils.Jackson;

import java.util.List;

/**
 * @ClassName: AccountAssembler
 * @Created: 2024/12/9 20:07
 * @Author ken
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PolicyConverter {
    PolicyConverter INSTANCE = Mappers.getMapper(PolicyConverter.class);

    PolicyDetailResp convert(Policy policy);

    default List<PolicyDetailResp> convert(List<Policy> policies) {
        return policies.stream().map(policy -> Jackson.fromJsonString(policy.getContent(), PolicyDetailResp.class)).toList();
    }

    PolicyListResp toList(Policy policy);

    List<PolicyListResp> toList(List<Policy> policy);
}
