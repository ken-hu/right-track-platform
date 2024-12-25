package pers.ken.rt.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <name> TreeUtils </name>
 * <desc> 构建树形结构的通用工具 </desc>
 * Creation Time: 2021/10/8 22:43.
 *
 * @author _Ken.Hu
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeUtils {
    /**
     * 将平铺的 List 转化为树形结构
     *
     * @param list           平铺的列表数据
     * @param idGetter       获取节点唯一标识的函数
     * @param parentIdGetter 获取父节点标识的函数
     * @param childrenGetter 获取子节点列表的函数
     * @param childrenSetter 设置子节点列表的函数
     * @param <T>            节点类型
     * @param <ID>           节点唯一标识类型
     * @return 树形结构的列表
     */
    public static <T, ID> List<T> buildTree(List<T> list,
                                            Function<T, ID> idGetter,
                                            Function<T, ID> parentIdGetter,
                                            Function<T, List<T>> childrenGetter,
                                            BiConsumer<T, List<T>> childrenSetter,
                                            Function<T, Boolean> isRootNode) {
        // 将列表转为 Map，便于通过 ID 查找
        Map<ID, T> itemById = list.stream().collect(Collectors.toMap(idGetter, Function.identity()));

        // 创建一个结果集存放最终的树形结构
        List<T> roots = new ArrayList<>();

        // 遍历每个节点，构建树形结构
        for (T item : list) {
            ID parentId = parentIdGetter.apply(item);
            if (isRootNode.apply(item)) {
                // 如果 parentId 是 null，表示是根节点
                roots.add(item);
            } else {
                // 如果是子节点，找到父节点并设置子节点
                T parent = itemById.get(parentId);
                if (parent != null) {
                    // 获取父节点的子节点列表
                    List<T> children = childrenGetter.apply(parent);
                    if (children == null) {
                        // 如果子节点列表为 null，初始化新的列表
                        children = new ArrayList<>();
                        childrenSetter.accept(parent, children);
                    }
                    // 添加当前节点到父节点的子节点列表
                    children.add(item);
                }
            }
        }

        return roots;
    }


    /**
     * 将平铺的 List 转化为树形结构
     *
     * @param nodes           平铺的列表数据
     * @param idGetter       获取节点唯一标识的函数
     * @param parentIdGetter 获取父节点标识的函数
     * @param childrenGetter 获取子节点列表的函数
     * @param childrenSetter 设置子节点列表的函数
     * @param <T>            节点类型
     * @param <ID>           节点唯一标识类型
     * @return 树形结构的列表
     */
    public static <T, S, ID> List<T> buildTree(List<S> nodes,
                                               Function<S, T> nodeConverter,
                                               Function<T, ID> idGetter,
                                               Function<T, ID> parentIdGetter,
                                               Function<T, List<T>> childrenGetter,
                                               BiConsumer<T, List<T>> childrenSetter,
                                               Function<T, Boolean> isRootNode) {
        List<T> treeNodes = nodes.stream().map(nodeConverter).toList();
        return buildTree(treeNodes, idGetter, parentIdGetter, childrenGetter, childrenSetter, isRootNode);
    }
}
