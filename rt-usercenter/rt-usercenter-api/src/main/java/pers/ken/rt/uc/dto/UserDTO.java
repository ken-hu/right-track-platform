package pers.ken.rt.uc.dto;

import lombok.Data;

/**
 * <code> UserDTO </code>
 * <desc> UserDTO </desc>
 * <b>Creation Time:</b> 2021/10/19 0:09.
 *
 * @author _Ken.Hu
 */
@Data
public class UserDTO {
    private Long id;

    private String username;

    private String password;
}
