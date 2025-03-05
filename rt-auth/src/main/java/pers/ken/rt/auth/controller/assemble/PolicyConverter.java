package pers.ken.rt.auth.controller.assemble;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.dto.resp.PolicyDetailResponse;
import pers.ken.rt.auth.dto.resp.PolicyListResponse;
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

    PolicyDetailResponse convert(Policy policy);

    default List<PolicyDetailResponse> convert(List<Policy> policies) {
        return policies.stream().map(policy -> Jackson.fromJsonString(policy.getContent(), PolicyDetailResponse.class)).toList();
    }

    PolicyListResponse toList(Policy policy);

}
