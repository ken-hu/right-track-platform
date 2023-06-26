package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Ken
 * @className: PageRequest
 * @createdTime: 2023/3/8 1:41
 * @desc:
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageReq<T> {
    private Integer page;
    private Integer size;
    private String orderBy;
    private String sort;
    private T filters;

    public static PageReq<Void> of(Integer page, Integer size, String orderBy, String sort) {
        return new PageReq<>(page, size, orderBy, sort, null);
    }

    public static PageReq<Void> of(Integer page, Integer size) {
        return new PageReq<>(page, size, null, null, null);
    }

    public static <T> PageReq<T> of(Integer page, Integer size, String orderBy, String sort, T filters) {
        return new PageReq<>(page, size, orderBy, sort, filters);
    }
}
