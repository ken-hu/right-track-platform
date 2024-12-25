package pers.ken.rt.map.domain.entity.aggregate;

import lombok.Data;

/**
 * @ClassName: Region
 * @Created: 2024/11/4 18:30
 * @Author ken
 */
@Data
public class Region {
    private Integer adcode;
    private String name;
    private Integer parent;
}
