package pers.ken.rt.auth.application.service;

import pers.ken.rt.auth.domain.entity.valueobj.UserStatus;
import pers.ken.rt.auth.interfaces.dto.resp.UserItemResponse;
import pers.ken.rt.common.model.PageQuery;
import pers.ken.rt.common.model.PageResult;

/**
 * The interface User app service.
 *
 * @author Ken
 * @className: AuthorizeAppService
 * @createdTime: 2023 /3/7 0:06
 * @desc:
 */
public interface UserAppService {
    /**
     * Page query items page result.
     *
     * @param pageReq the page request
     * @return the page result
     */
    PageResult<UserItemResponse> pageQuery(PageQuery pageReq);

    /**
     * Update user status.
     *
     * @param userId     the user id
     * @param userStatus the user status
     */
    void updateUserStatus(Long userId, UserStatus userStatus);
}