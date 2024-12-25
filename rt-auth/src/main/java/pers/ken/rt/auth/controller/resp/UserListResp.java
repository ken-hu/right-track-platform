package pers.ken.rt.auth.controller.resp;

import lombok.Data;

/**
 * @ClassName: UserListResp
 * @Created: 2024/12/9 17:12
 * @Author ken
 */
@Data
public class UserListResp {
    private Integer id;
    private String username;
    private String nickname;
}
