package pers.ken.rt.auth.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.ken.rt.auth.repository.po.UserGroup;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【user_group(用户组)】的数据库操作Mapper
 * @createDate 2024-12-10 10:39:52
 * @Entity pers.ken.rt.auth.repository.po.UserGroup
 */
public interface UserGroupMapper extends BaseMapper<UserGroup> {

    void insertUserGroupRel(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

    List<UserGroup> selectUserGroups(@Param("userId") Integer userId);

    void insertUserGroupPolicyRel(@Param("groupId") Integer groupId, @Param("policyId") Integer policyId);
}




