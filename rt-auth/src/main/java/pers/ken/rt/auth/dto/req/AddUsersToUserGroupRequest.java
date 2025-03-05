package pers.ken.rt.auth.dto.req;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: AddUsersToUserGroupReq
 * @Created: 2024/12/10 14:00
 * @Author ken
 */
@Data
public class AddUsersToUserGroupRequest {
    private List<Integer> userIds;
}
