package pers.ken.rt.auth.infrastructure.repository.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.ken.rt.auth.infrastructure.repository.persistence.po.PolicyPO;

import java.util.List;

/**
 * The interface Policy mapper.
 *
 * @author 54554
 * @description 针对表 【policy】的数据库操作Mapper
 * @createDate 2023 -03-22 10:57:43
 * @Entity pers.ken.rt.auth.infrastructure.repository.persistence.po.PolicyPO
 */
public interface PolicyMapper extends BaseMapper<PolicyPO> {
    /**
     * Select all by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<PolicyPO> selectAllByUserId(@Param("userId") Long userId);

    /**
     * Select all by role id list.
     *
     * @param roleId the role id
     * @return the list
     */
    List<PolicyPO> selectAllByRoleId(@Param("userId") Long roleId);
}




