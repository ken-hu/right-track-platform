package pers.ken.rt.auth.controller.assemble;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.dto.resp.TenantDetailGetResponse;
import pers.ken.rt.auth.dto.resp.TenantListResponse;
import pers.ken.rt.auth.repository.po.Tenant;

/**
 * The interface Tenant converter.
 *
 * @ClassName: TenantAssembler
 * @Created: 2024 /12/9 21:47
 * @Author ken
 */
@Mapper
public interface TenantConverter {
    /**
     * The constant INSTANCE.
     */
    TenantConverter INSTANCE = Mappers.getMapper(TenantConverter.class);

    /**
     * Convert tenant detail get response.
     *
     * @param tenant the tenant
     * @return the tenant detail get response
     */
    TenantDetailGetResponse convert(Tenant tenant);

    /**
     * To list response tenant list response.
     *
     * @param tenant the tenant
     * @return the tenant list response
     */
    TenantListResponse toListResponse(Tenant tenant);
}
