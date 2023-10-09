package Algorithm.leetcode;

//965. 单值二叉树

// 如果二叉树每个节点都具有相同的值，那么该二叉树就是单值二叉树。
// 只有给定的树是单值二叉树时，才返回 true；否则返回 false。

// 示例 1：

// 输入：[1,1,1,1,1,null,1]
// 输出：true
// 示例 2：

// 输入：[2,2,2,5,2]
// 输出：false

public class IsUnivalTree {

    // 深度优先遍历，当一个子树的值与其左右子树不同时直接返回false，如果相同继续进行迭代遍历。
    public boolean isUnivalTree(TreeNode root){
        if (root == null) {
            return false;
        }
        if (root.left != null ) {
            if (root.val != root.left.val || !isUnivalTree(root.left)){
                return false;
            }
        }
        if (root.right != null) {
            if (root.val != root.right.val || !isUnivalTree(root.right)) {
                return false;
            }
        }
        return true;
    }
    
}
