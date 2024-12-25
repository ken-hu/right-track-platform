package pers.ken.rt.map.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName region
 */
@TableName(value = "region")
@Data
public class RegionPO implements Serializable {
    private Integer adcode;

    private String name;

    private Integer provinceCode;

    private String provinceName;

    private Integer cityCode;

    private String cityName;

    private Integer districtCode;

    private String districtName;

    private Integer parent;

    private String adlevel;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}