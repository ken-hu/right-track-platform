package pers.ken.rt.auth.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.ken.rt.common.model.Pagination;

/**
 * @ClassName: RoleListReq
 * @Created: 2024/12/26 20:50
 * @Author ken
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleListRequest extends Pagination {
}
