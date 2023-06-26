package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * <name> PageVO </name>
 * <desc> PageVO </desc>
 * Creation Time: 2021/10/8 22:39.
 *
 * @author _Ken.Hu
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    private Integer page;
    private Integer pageSize;
    private Long totalCount;
    private List<T> records;

    public static <T> PageResult<T> of(Integer page, Integer pageSize, Long totalCount, List<T> records) {
        return new PageResult<>(page, pageSize, totalCount, records);
    }

    public static <T> PageResult<T> of(PageResult<?> pageResult, List<T> records) {
        return new PageResult<>(pageResult.getPage(), pageResult.getPageSize(), pageResult.getTotalCount(), records);
    }

}
