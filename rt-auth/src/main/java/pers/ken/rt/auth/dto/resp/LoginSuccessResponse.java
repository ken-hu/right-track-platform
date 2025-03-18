package pers.ken.rt.auth.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: LoginSuccessResp
 * @Created: 2024/11/20 21:31
 * @Author ken
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessResponse {
    private String message = "Login success";
}
