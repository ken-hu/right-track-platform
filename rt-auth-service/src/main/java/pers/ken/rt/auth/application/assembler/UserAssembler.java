package pers.ken.rt.auth.application.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.domain.entity.aggregate.User;
import pers.ken.rt.auth.interfaces.dto.resp.UserItemResponse;

import java.util.List;

/**
 * The interface User app converter.
 *
 * @author Ken
 * @className: UserAppConverter
 * @createdTime: 2023 /3/8 18:37
 * @desc:
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAssembler {
    UserAssembler INSTANCE = Mappers.getMapper(UserAssembler.class);

    /**
     * Convert user item response.
     *
     * @param user the user
     * @return the user item response
     */
    @Mappings({
            @Mapping(source = "username.name", target = "username")
    })
    UserItemResponse convert(User user);

    /**
     * Convert list.
     *
     * @param users the users
     * @return the list
     */
    List<UserItemResponse> convert(List<User> users);
}
