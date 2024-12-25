package pers.ken.rt.auth.controller.req;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: AddUsersToUserGroupReq
 * @Created: 2024/12/10 14:00
 * @Author ken
 */
@Data
public class AddUsersToUserGroupReq {
    private List<Integer> userIds;
}
