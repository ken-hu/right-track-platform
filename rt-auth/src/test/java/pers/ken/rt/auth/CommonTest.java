package pers.ken.rt.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.pattern.PathPatternParser;
import pers.ken.rt.common.utils.Jackson;
import pers.ken.rt.common.utils.TreeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <name> CommonTest </name>
 * <desc> CommonTest </desc>
 * Creation Time: 2021/10/7 16:45.
 *
 * @author _Ken.Hu
 */
class CommonTest {
    public static class TreeNode {
        private Long id;
        private Long parentId;
        private String name;
        private List<TreeNode> children;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<TreeNode> children) {
            this.children = children;
        }
    }

    public static TreeNode createNode(Long id, Long parentId, String name) {
        TreeNode node = new TreeNode();
        node.setId(id);
        node.setParentId(parentId);
        node.setName(name);
        return node;
    }

    @Test
    public void treeTest() {
        // 构造平铺的节点数据
        List<TreeNode> flatList = new ArrayList<>();
        flatList.add(createNode(1L, null, "Root"));
        flatList.add(createNode(2L, 1L, "Child 1"));
        flatList.add(createNode(3L, 1L, "Child 2"));
        flatList.add(createNode(4L, 2L, "Grandchild 1"));
        flatList.add(createNode(5L, 2L, "Grandchild 2"));

        // 构建树形结构
        List<TreeNode> tree = TreeUtils.buildTree(flatList,
                TreeNode::getId,
                TreeNode::getParentId,
                TreeNode::getChildren,
                TreeNode::setChildren,
                treeNode -> null == treeNode.getParentId());

        System.out.println("Tree structure: " + Jackson.toJsonString(tree));
    }

    @Test
    void passwordTest() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    void encodeConfig() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        PathPatternParser parser = PathPatternParser.defaultInstance;
//        PathPattern parse = parser.parse("channel:mars:.*");
//        boolean match = parse.matches(PathContainer.parsePath("channel:mars:adcode/1/icCode/2"));
//        boolean match = antPathMatcher.match("mars:adcode/*/icCode/1*", "mars:adcode/1/icCode/2");
        boolean match = antPathMatcher.match("channel:mars:*", "channel:mars:getDesc");
        if (match) {
            System.out.println("Nice");
        } else {
            System.out.println("So bad");
        }
    }
}
