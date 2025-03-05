package pers.ken.rt.auth.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.ken.rt.common.model.Pagination;

/**
 * @ClassName: UserGroupListReq
 * @Created: 2024/12/26 18:54
 * @Author ken
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserGroupListRequest extends Pagination {
}
