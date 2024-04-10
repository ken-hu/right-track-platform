package pers.ken.rt.auth.infrastructure.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.ken.rt.common.model.PageResult;
import pers.ken.rt.common.model.Pagination;

import java.util.List;
import java.util.function.Function;

/**
 * @ClassName: Pages
 * @Created: 2023/7/2 18:51
 * @Desc:
 * @Author ken
 */
public class Pages {
    private Pages() {
    }

    public static <T, R> PageResult<R> convert(Page<T> page, Function<T, R> fun) {
        Pagination pagination = getPagination(page);
        List<R> records = assemble(page, fun);
        return new PageResult<>(pagination, records);
    }

    public static <T, R> PageResult<R> convert(Page<T> page, List<R> data) {
        Pagination pagination = getPagination(page);
        return new PageResult<>(pagination, data);
    }

    public static <T> PageResult<T> convert(List<T> data) {
        Pagination pagination = new Pagination(1, -1, data.size());
        return new PageResult<>(pagination, data);
    }
    public static <T> PageResult<T> convert(Page<T> data) {
        Pagination pagination = getPagination(data);
        return new PageResult<>(pagination, data.getRecords());
    }

    public static <T, R> PageResult<R> convert(List<T> data, Function<T, R> fun) {
        List<R> records = data.stream().map(fun).toList();
        return new PageResult<>(null, records);
    }

    public static Pagination getPagination(Page<?> page) {
        int total = (int) page.getTotal();
        if (page.getSize() == -1) {
            total = page.getRecords().size();
        }
        return new Pagination((int) page.getCurrent(), (int) page.getSize(), total);
    }

    public static <T, R> List<R> assemble(Page<T> page, Function<T, R> fun) {
        return page.getRecords().stream()
                .map(fun)
                .toList();
    }
}
