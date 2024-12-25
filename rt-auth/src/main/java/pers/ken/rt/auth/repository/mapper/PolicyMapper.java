package pers.ken.rt.auth.repository.mapper;

import org.apache.ibatis.annotations.Param;
import pers.ken.rt.auth.repository.po.Policy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【policy】的数据库操作Mapper
 * @createDate 2024-12-07 15:19:39
 * @Entity pers.ken.rt.auth.repository.po.Policy
 */
public interface PolicyMapper extends BaseMapper<Policy> {

    void insertUserPolicyRel(@Param("userId") Integer userId, @Param("policyId") Integer policyId);

    void deleteUserPolicyRelByPolicyIds(@Param("userId") Integer userId, @Param("policyIds") List<Integer> policyIds);

    void insertUserGroupPolicyRel(@Param("groupId") Integer groupId, @Param("policyId") Integer policyId);

    List<Policy> selectPoliciesByUser(@Param("userId") Integer userId);
}




