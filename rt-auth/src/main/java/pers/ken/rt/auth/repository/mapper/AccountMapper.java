package pers.ken.rt.auth.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.ken.rt.auth.repository.po.Account;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【account(账户信息)】的数据库操作Mapper
 * @createDate 2024-12-05 16:33:02
 * @Entity pers.ken.rt.auth.repository.po.Account
 */
public interface AccountMapper extends BaseMapper<Account> {

    List<Account> selectByUserGroup(@Param("userGroupId") Integer userGroupId);

}




