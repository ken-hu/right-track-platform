package pers.ken.rt.auth.controller.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: LoginFailureResp
 * @Created: 2024/11/22 15:44
 * @Author ken
 */
@Data
@AllArgsConstructor
public class LoginFailureResp {
    private String message;
}
