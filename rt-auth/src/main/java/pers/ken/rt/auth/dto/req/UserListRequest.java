package pers.ken.rt.auth.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.ken.rt.common.model.Pagination;

/**
 * @ClassName: UserListReq
 * @Created: 2023/10/31 18:14
 * @Desc:
 * @Author Ken
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserListRequest extends Pagination {
}
