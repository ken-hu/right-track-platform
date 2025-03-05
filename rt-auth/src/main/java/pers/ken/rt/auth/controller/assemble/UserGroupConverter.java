package pers.ken.rt.auth.controller.assemble;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.dto.resp.UserGroupGetResponse;
import pers.ken.rt.auth.dto.resp.UserGroupListResponse;
import pers.ken.rt.auth.repository.po.UserGroup;

import java.util.List;

/**
 * @ClassName: UserGroupAssembler
 * @Created: 2024/12/10 10:50
 * @Author ken
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserGroupConverter {
    UserGroupConverter INSTANCE = Mappers.getMapper(UserGroupConverter.class);

    UserGroupListResponse toList(UserGroup userGroup);

    List<UserGroupListResponse> toList(List<UserGroup> userGroup);

    UserGroupGetResponse toGetResponse(UserGroup group);
}
