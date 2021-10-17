package pers.ken.rt.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import pers.ken.rt.common.model.ITreeNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * <name> TreeUtils </name>
 * <desc> 构建树形结构的通用工具 </desc>
 * Creation Time: 2021/10/8 22:43.
 *
 * @author _Ken.Hu
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeUtils {
    public static <S, T extends ITreeNode<S, T>> List<T> generateTrees(List<T> nodes) {
        List<T> roots = new ArrayList<>();
        for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
            T node = ite.next();
            if (node.root()) {
                roots.add(node);
                // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                ite.remove();
            }
        }
        roots.forEach(r -> setChildren(r, nodes));
        return roots;
    }

    public static <S, T extends ITreeNode<S, T>> void setChildren(T parent, List<T> nodes) {
        List<T> children = new ArrayList<>();
        Object parentId = parent.id();
        for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
            T node = ite.next();
            if (Objects.equals(node.parentId(), parentId)) {
                children.add(node);
                // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                ite.remove();
            }
        }
        // 如果孩子为空，则直接返回,否则继续递归设置孩子的孩子
        if (children.isEmpty()) {
            return;
        }
        parent.setChildrenNodes(children);

        children.forEach(m -> {
            // 递归设置子节点
            setChildren(m, nodes);
        });
    }

    public static <S, T extends ITreeNode<S, T>> List<T> getLeaves(T parent) {
        List<T> leaves = new ArrayList<>();
        fillLeaves(parent, leaves);
        return leaves;
    }

    /**
     * 将parent的所有叶子节点填充至leafs列表中
     *
     * @param parent 父节点
     * @param leaves  叶子节点列表
     * @param <T>    实际节点类型
     */
    public static <S, T extends ITreeNode<S, T>> void fillLeaves(T parent, List<T> leaves) {
        List<T> childrenNodes = (List<T>) parent.getChildrenNodes();
        // 如果节点没有子节点则说明为叶子节点
        if (CollectionUtils.isEmpty(childrenNodes)) {
            leaves.add(parent);
            return;
        }
        // 递归调用子节点，查找叶子节点
        for (T childrenNode : childrenNodes) {
            fillLeaves(childrenNode, leaves);
        }
    }
}
