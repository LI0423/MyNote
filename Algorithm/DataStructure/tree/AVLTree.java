package Algorithm.DataStructure.tree;

public class AVLTree {

    class TreeNode {
        public int val;
        public int height;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            this.val = x;
        }
    }

    private TreeNode root;

    // 获取节点高度
    public int height(TreeNode node) {
        // 空节点高度为 -1，叶节点高度为 0
        return node == null ? -1 : node.height;
    }

    // 更新节点高度
    private void updateHeight(TreeNode node) {
        // 节点高度等于最高子树高度+1
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    // 获取节点平衡因子
    private int balanceFactor(TreeNode node) {
        // 空节点平衡因子为0
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    // 右旋操作
    private TreeNode rightRotate(TreeNode node) {
        TreeNode child = node.left;
        TreeNode grandChild = child.right;
        // 以child为原点，将node向右旋转
        child.right = node;
        node.left = grandChild;
        // 更新节点高度
        updateHeight(node);
        updateHeight(child);
        // 返回旋转后子树的根节点
        return child;
    }

    // 左旋操作
    private TreeNode leftRotate(TreeNode node) {
        TreeNode child = node.right;
        TreeNode grandChild = child.left;
        // 以child为原点，将node向左旋转
        child.left = node;
        node.right = grandChild;
        // 更新节点高度
        updateHeight(node);
        updateHeight(child);
        // 返回旋转后子树的根节点
        return child;
    }

    // 执行旋转操作，使该子树重新恢复平衡
    private TreeNode rotate(TreeNode node) {
        // 获取节点node的平衡因子
        int balanceFactor = balanceFactor(node);
        // 左偏树
        if (balanceFactor > 1) {
            if (balanceFactor(node.left) >= 0) {
                // 右旋
                return rightRotate(node);
            } else {
                // 先左旋后右旋
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        // 右偏树
        if (balanceFactor < -1) {
            if (balanceFactor(node.right) <= 0) {
                // 左旋
                return leftRotate(node);
            } else {
                // 先右旋后左旋
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        // 平衡树，无需旋转，直接返回
        return node;
    }

    // 插入节点
    public void insert(int val) {
        root = insertHelper(root, val);
    }

    // 递归插入
    private TreeNode insertHelper(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val);
        }
        // 查找插入位置并插入节点
        if (val < node.val) {
            node.left = insertHelper(node.left, val);
        } else if (val > node.val) {
            node.right = insertHelper(node.right, val);
        } else {
            // 重复节点不插入，直接返回
            return node;
        }
        // 更新节点高度
        updateHeight(node);
        // 执行旋转操作，使该子树重新平衡
        node = rotate(node);

        // 返回子树的根节点
        return node;
    }

    // 删除节点
    public void remove(TreeNode node, int val) {
        root = removeHelper(node, val);
    }

    // 递归删除节点
    private TreeNode removeHelper(TreeNode node, int val) {
        if (node == null) {
            return null;
        }
        // 查找节点并删除
        if (val < node.val) {
            node.left = removeHelper(node.left, val);
        } else if (val > node.val) {
            node.right = removeHelper(node.right, val);
        } else {
            if (node.left == null || node.right == null) {
                TreeNode child = node.left != null ? node.left : node.right;
                // 子节点数量 = 0，直接删除node并返回
                if (child == null){
                    return null;
                } else {
                    // 子节点数量 = 1，直接删除node
                    node = child;
                }
            } else {
                // 子节点数量=2，则将中序遍历的下个节点删除，并用该节点替换当前节点
                TreeNode tmp = node.right;
                while (tmp.left != null) {
                    tmp = tmp.left;
                }
                node.right = removeHelper(node.right, tmp.val);
                node.val = tmp.val;
            }
        }
        // 更新节点高度
        updateHeight(node);
        // 执行旋转操作
        node = rotate(node);
        // 返回子树的根节点
        return node;
    }

}