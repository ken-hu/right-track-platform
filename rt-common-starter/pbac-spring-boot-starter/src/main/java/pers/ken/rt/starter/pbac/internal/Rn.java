package pers.ken.rt.starter.pbac.internal;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: Rn
 * @Created: 2024/7/5
 * @Author ken
 */
@Data
@Builder
public class Rn {
    private String rid;
    private String val;
    private String rn;
    private List<String> rnRaw;
}
