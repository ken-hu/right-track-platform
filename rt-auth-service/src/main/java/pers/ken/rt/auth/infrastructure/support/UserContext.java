package pers.ken.rt.auth.infrastructure.support;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Ken
 * @className: UserContext
 * @createdTime: 2023/2/27 22:02
 * @desc:
 */
@Data
@AllArgsConstructor
public class UserContext {
    private Long userId;
    private String username;
    private List<String> clientIds;
}
