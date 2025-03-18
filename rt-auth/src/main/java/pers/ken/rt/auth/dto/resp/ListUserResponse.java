package pers.ken.rt.auth.dto.resp;

import lombok.Data;

/**
 * @ClassName: UserListResp
 * @Created: 2024/12/9 17:12
 * @Author ken
 */
@Data
public class ListUserResponse {
    private Integer id;
    private String username;
    private String nickname;
}
