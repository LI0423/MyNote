package Algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

//993. 二叉树的堂兄弟节点

// 在二叉树中，根节点位于深度 0 处，每个深度为 k 的节点的子节点位于深度 k+1 处。
// 如果二叉树的两个节点深度相同，但 父节点不同 ，则它们是一对堂兄弟节点。
// 我们给出了具有唯一值的二叉树的根节点 root ，以及树中两个不同节点的值 x 和 y 。
// 只有与值 x 和 y 对应的节点是堂兄弟节点时，才返回 true 。否则，返回 false。

// 示例 1：

// 输入：root = [1,2,3,4], x = 4, y = 3
// 输出：false
// 示例 2：

// 输入：root = [1,2,3,null,4,null,5], x = 5, y = 4
// 输出：true
// 示例 3：

// 输入：root = [1,2,3,null,4], x = 2, y = 3
// 输出：false

public class IsCousins {

    // x 的信息
    int x;
    TreeNode xParent;
    int xDepth;
    boolean xFound = false;

    // y 的信息
    int y;
    TreeNode yParent;
    int yDepth;
    boolean yFound = false;

    // 深度优先搜索
    public boolean isCousins(TreeNode root, int x, int y) {
        this.x = x;
        this.y = y;
        dfs(root, 0, null);
        return xDepth == yDepth && xParent != yParent;
    }

    public void dfs(TreeNode root, int depth, TreeNode parent) {
        if (root == null) {
            return;
        }
        if (root.val == x) {
            xParent = parent;
            xDepth = depth;
            xFound = true;
        } else if (root.val == y) {
            yParent = parent;
            yDepth = depth;
            yFound = true;
        }
        if (xFound && yFound) {
            return;
        }
        dfs(root.left, depth + 1, parent);
        if (xFound && yFound) {
            return;
        }
        dfs(root.right, depth + 1, parent);
    }
    
    // 广度优先搜索
    public boolean isCousins2(TreeNode root, int x, int y) {
        this.x = x;
        this.y = y;
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> depthQueue = new LinkedList<>();
        nodeQueue.offer(root);
        depthQueue.offer(0);
        
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int depth = depthQueue.poll();
            if (node.left != null) {
                nodeQueue.offer(node.left);
                depthQueue.offer(depth + 1);
                update(node.left, node, depth + 1);
            }
            if (node.right != null) {
                nodeQueue.offer(node.right);
                depthQueue.offer(depth + 1);
                update(root.right, node, depth + 1);
            }
            if (xFound && yFound) {
                break;
            }
        }
        return xDepth == yDepth && xParent != yParent;
    }

    public void update(TreeNode root, TreeNode parent, int depth) {
        if (root.val == x) {
            xParent = parent;
            xDepth = depth;
            xFound = true;
        }
        if (root.val == y) {
            yParent = parent;
            yDepth = depth;
            yFound = true;
        }
    }
}
