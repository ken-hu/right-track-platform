package pers.ken.rt.common.model;

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
public class PageResp<T> {
    private Long totalSize;
    private Long totalPage;
    private List<T> records;
}
