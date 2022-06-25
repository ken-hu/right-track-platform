package pers.ken.rt.common.iam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

/**
 * <code> UserAuthority </code>
 * <desc> UserAuthority </desc>
 * <b>Creation Time:</b> 2022/3/8 20:45.
 *
 * @author _Ken.Hu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class UserAuthority {
    private List<Policy> policies;
    private String actionName;
    private String resource;
    private String permissionCode;
}
