package pers.ken.rt.auth.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @ClassName: PasswordRestReq
 * @Created: 2024/12/9 19:55
 * @Author ken
 */
@Data
public class PasswordRestRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
