package pers.ken.rt.auth.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.ken.rt.common.model.Pagination;

/**
 * @ClassName: PolicyListReq
 * @Created: 2024/12/26 11:16
 * @Author ken
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListPolicyRequest extends Pagination {
    private Integer applicationId;

    private Integer roleId;

    private Integer userGroupId;

    private Integer userId;
}
