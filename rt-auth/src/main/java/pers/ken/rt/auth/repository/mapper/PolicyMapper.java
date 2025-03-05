package pers.ken.rt.auth.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.ken.rt.auth.repository.po.Policy;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【policy】的数据库操作Mapper
 * @createDate 2024-12-07 15:19:39
 * @Entity pers.ken.rt.auth.repository.po.Policy
 */
public interface PolicyMapper extends BaseMapper<Policy> {

    void deleteUserPolicyRelByPolicyIds(@Param("userId") Integer userId, @Param("policyIds") List<Integer> policyIds);

    List<Policy> selectPoliciesByUser(@Param("userId") Integer userId);

}




