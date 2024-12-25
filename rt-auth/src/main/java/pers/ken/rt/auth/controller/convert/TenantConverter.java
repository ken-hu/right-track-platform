package pers.ken.rt.auth.controller.convert;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.controller.resp.TenantDetailResp;
import pers.ken.rt.auth.controller.resp.TenantListResp;
import pers.ken.rt.auth.repository.po.Tenant;

/**
 * @ClassName: TenantAssembler
 * @Created: 2024/12/9 21:47
 * @Author ken
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TenantConverter {
    TenantConverter INSTANCE = Mappers.getMapper(TenantConverter.class);

    TenantDetailResp convert(Tenant tenant);

    TenantListResp toList(Tenant tenant);
}
