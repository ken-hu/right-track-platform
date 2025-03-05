package pers.ken.rt.auth.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: AssignApplicationReq
 * @Created: 2024/12/23 11:17
 * @Author ken
 */
@Data
public class AssignApplicationRequest {
    @NotEmpty
    private List<Integer> applicationIds;
}
