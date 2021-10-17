package pers.ken.rt.common.model;

import java.util.List;

/**
 * <name> ITreeNode </name>
 * <desc> ITreeNode </desc>
 * Creation Time: 2021/10/8 22:40.
 *
 * @author _Ken.Hu
 */
public interface ITreeNode<T, S extends ITreeNode<T, S>> {
    /**
     * 获取节点id
     *
     * @return
     */
     T id();

    /**
     * 获取该节点的父节点id
     *
     * @return
     */
     T parentId();

    /**
     * 是否是根节点
     *
     * @return
     */
     boolean root();

    /**
     * 设置节点的子节点列表
     *
     * @param children
     */
     void setChildrenNodes(List<S> children);

    /**
     * 获取所有子节点
     *
     * @return
     */
     Iterable<S> getChildrenNodes();
}
