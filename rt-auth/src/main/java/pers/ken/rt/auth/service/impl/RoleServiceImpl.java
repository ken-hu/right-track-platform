package pers.ken.rt.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.ken.rt.auth.repository.po.Role;
import pers.ken.rt.auth.service.RoleService;
import pers.ken.rt.auth.repository.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 * @description 针对表【role(角色)】的数据库操作Service实现
 * @createDate 2024-12-24 18:02:42
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

}




