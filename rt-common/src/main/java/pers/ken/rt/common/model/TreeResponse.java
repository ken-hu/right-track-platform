package pers.ken.rt.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: TreeResponse
 * @Created: 2024/12/12 11:47
 * @Author ken
 */
@Data
@AllArgsConstructor
public class TreeResponse<T> {
    private List<T> tree;


    public static <T> TreeResponse<T> of(List<T> nodes) {
        return new TreeResponse<>(nodes);
    }
}
