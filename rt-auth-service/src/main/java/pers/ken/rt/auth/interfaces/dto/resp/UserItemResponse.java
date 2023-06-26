package pers.ken.rt.auth.interfaces.dto.resp;

import lombok.Data;
import pers.ken.rt.auth.domain.entity.valueobj.UserStatus;

/**
 * @author Ken
 * @className: UserItemResponse
 * @createdTime: 2023/3/8 18:25
 * @desc:
 */
@Data
public class UserItemResponse {
    private Long userId;
    private String username;
    private UserStatus userStatus;
}
