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
public class PageResult<T> {
    private Pagination pagination;
    private List<T> data;

    public static <T> PageResult<T> of(Pagination pagination, List<T> data) {
        return new PageResult<>(pagination, data);
    }


    public <S> PageResult<S> convert(PageResult<T> origin, List<S> data) {
        Pagination pagination = origin.getPagination();
        return new PageResult<S>(pagination, data);
    }

    public <S,A> PageResult<S> convert(PageResult<T> origin, Function<List<T>, List<S>> fun) {
        Pagination pagination = origin.getPagination();
        List<S> data = fun.apply(origin.getData());
        return new PageResult<S>(pagination, data);
    }
}
