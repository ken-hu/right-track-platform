package pers.ken.rt.auth.dto.req;

import jakarta.validation.constraints.NotBlank;
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
public class PolicyListRequest extends Pagination {
    @NotBlank
    private String applicationCode;
}
