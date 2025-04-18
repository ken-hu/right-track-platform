package pers.ken.rt.auth.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.ken.rt.common.model.Pagination;

/**
 * @ClassName: ListDepartmentsRequest
 * @Created: 2025/3/18 17:26
 * @Author ken
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListDepartmentsRequest extends Pagination {
    private String parentCode;
}
