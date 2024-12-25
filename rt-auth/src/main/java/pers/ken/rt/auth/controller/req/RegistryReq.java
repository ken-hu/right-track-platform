package pers.ken.rt.auth.controller.req;

import lombok.Data;

/**
 * @author Ken
 * @className: RegistryReq
 * @createdTime: 2023/3/8 12:07
 * @desc:
 */
@Data
public class RegistryReq {
    private String username;
    private String password;
}
