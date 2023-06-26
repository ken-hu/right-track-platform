package pers.ken.rt.auth.application.service.impl;

import org.springframework.stereotype.Service;
import pers.ken.rt.auth.application.service.GroupAppService;
import pers.ken.rt.auth.domain.repository.GroupRepository;

/**
 * @author Ken
 * @className: GroupAppServiceImpl
 * @createdTime: 2023/5/24 15:21
 * @desc:
 */
@Service
public class GroupAppServiceImpl implements GroupAppService {
    private GroupRepository groupRepository;
    @Override
    public void pageQuery() {

    }
}
