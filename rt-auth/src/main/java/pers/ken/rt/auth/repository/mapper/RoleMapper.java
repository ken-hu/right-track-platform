package pers.ken.rt.auth.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.ken.rt.auth.repository.po.Role;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【role(角色)】的数据库操作Mapper
 * @createDate 2024-12-24 18:02:42
 * @Entity pers.ken.rt.auth.repository.po.Role
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectByUser(@Param("userId") Integer userId);

    void insertRolePolicyRel(@Param("roleId") Integer roleId, @Param("policyId") Integer policyId);

}




