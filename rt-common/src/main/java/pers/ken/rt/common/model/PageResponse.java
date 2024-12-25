package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.function.Function;

/**
 * <name> PageVO </name>
 * <desc> PageVO </desc>
 * Creation Time: 2021/10/8 22:39.
 *
 * @author _Ken.Hu
 */
@Data
@AllArgsConstructor
public class PageResponse<T> {
    private Pagination pagination;
    private List<T> records;

    public static <T> PageResponse<T> of(Pagination pagination, List<T> data) {
        return new PageResponse<>(pagination, data);
    }


    public <S> PageResponse<S> convert(PageResponse<T> origin, List<S> data) {
        Pagination pagination = origin.getPagination();
        return new PageResponse<S>(pagination, data);
    }

    public <S, A> PageResponse<S> convert(PageResponse<T> origin, Function<List<T>, List<S>> fun) {
        Pagination pagination = origin.getPagination();
        List<S> data = fun.apply(origin.getRecords());
        return new PageResponse<S>(pagination, data);
    }
}
