package pers.ken.rt.auth.controller.assemble;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.dto.req.RoleCreateRequest;
import pers.ken.rt.auth.dto.resp.RoleGetResponse;
import pers.ken.rt.auth.dto.resp.RoleListResponse;
import pers.ken.rt.auth.repository.po.Role;

import java.util.List;

/**
 * The interface Role converter.
 *
 * @ClassName: RoleConverter
 * @Created: 2024 /12/26 20:44
 * @Author ken
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleConverter {
    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    List<RoleListResponse> convert(List<Role> roles);

    RoleListResponse convert(Role role);

    Role toRole(RoleCreateRequest request);

    RoleGetResponse toGetResponse(Role role);
}
