package pers.ken.rt.common.model;

import lombok.Getter;

/**
 * @author Ken
 * @className: PageQuery
 * @createdTime: 2023/3/8 2:07
 * @desc:
 */
@Getter
public class PageQuery {
    private Integer page;
    private Integer size;
    private String orderBy;
    private String sort;

    private PageQuery() {
    }

    public PageQuery(Integer page, Integer size, String orderBy, String sort) {
        this.page = page;
        this.size = size;
        this.orderBy = orderBy;
        this.sort = sort;
    }

    public static PageQuery of(Integer page, Integer size, String orderBy, String sort) {
        return new PageQuery(page, size, orderBy, sort);
    }

    public static <T> PageQuery of(PageReq<T> req) {
        return new PageQuery(req.getPage(), req.getSize(), req.getOrderBy(), req.getSort());
    }

    public static PageQuery of(Integer page, Integer size) {
        return new PageQuery(page, size, null, null);
    }
}
