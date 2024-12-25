package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: PageResponse
 * @Created: 2023/7/21 14:36
 * @Desc:
 * @Author ken
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    private Integer page = 1;
    private Integer perPage = 10;
    private Integer total;
}
