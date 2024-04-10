package pers.ken.rt.auth.infrastructure.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pers.ken.rt.auth.domain.entity.aggregate.User;
import pers.ken.rt.auth.infrastructure.repository.persistence.po.UserPO;

import java.util.List;

/**
 * The interface User converter.
 *
 * @author Ken
 * @className: UserConverter
 * @createdTime: 2023 /3/8 11:53
 * @desc:
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {
    /**
     * The constant INSTANCE.
     */
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * Convert user.
     *
     * @param user the user
     * @return the user
     */
    @Mappings({
            @Mapping(source = "password", target = "password.password"),
            @Mapping(source = "username", target = "username.name")
    })
    User convert(UserPO user);


    /**
     * Convert list.
     *
     * @param users the users
     * @return the list
     */
    List<User> convert(List<UserPO> users);

}
