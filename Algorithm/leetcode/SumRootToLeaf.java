package Algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

//1022. 从根到叶的二进制数之和

// 给出一棵二叉树，其上每个结点的值都是 0 或 1 。每一条从根到叶的路径都代表一个从最高有效位开始的二进制数。
// 例如，如果路径为 0 -> 1 -> 1 -> 0 -> 1，那么它表示二进制数 01101，也就是 13 。
// 对树上的每一片叶子，我们都要找出从根到该叶子的路径所表示的数字。
// 返回这些数字之和。题目数据保证答案是一个 32 位 整数。

// 示例 1：

// 输入：root = [1,0,1,0,1,0,1]
// 输出：22
// 解释：(100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22
// 示例 2：

// 输入：root = [0]
// 输出：0

public class SumRootToLeaf {

    //后序遍历的访问顺序为：左子树——右子树——根节点。我们对根节点 root 进行后序遍历：
    //如果节点是叶子节点，返回它对应的数字 val。
    //如果节点是非叶子节点，返回它的左子树和右子树对应的结果之和。
    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }

    public int dfs(TreeNode root, int val) {
        if (root == null) {
            return 0;
        }
        val = (val << 1) | root.val;
        if (root.left == null && root.right == null) {
            return val;
        } 
        return dfs(root.left, val) + dfs(root.right, val);
    }

    //迭代
    // 我们用栈来模拟递归，同时使用一个 prev 指针来记录先前访问的节点。算法步骤如下：
    // 如果节点 root 非空，我们将不断地将它及它的左节点压入栈中。
    // 我们从栈中获取节点：
    // 该节点的右节点为空或者等于 prev，说明该节点的左子树及右子树都已经被访问，我们将它出栈。如果该节点是叶子节点，我们将它对应的数字 val 加入结果中。设置 prev 为该节点，设置 root 为空指针。
    // 该节点的右节点非空且不等于 prev，我们令 root 指向该节点的右节点。
    // 如果 root 为空指针或者栈空，中止算法，否则重复步骤 1。
    public int sumRootToLeaf2(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        int val = 0, ret = 0;
        TreeNode prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                val = (val << 1) | root.val;
                stack.push(root);
                root = root.left;
            }
            root = stack.peek();
            if (root.right == null || root.right == prev) {
                if (root.left == null && root.right == null) {
                    ret += val;
                }
                val >>= 1;
                stack.pop();
                prev = root;
                root = null;
            } else {
                root = root.right;
            }
        }
        return ret;
    }
    
}


// Python

// class SumRootToLeaf:
//     def sumRootToLeaf(self, root: Optional[TreeNode]) -> int:
//         def dfs(node: Optional[TreeNode], val: int) -> int:
//             if node is None:
//                 return 0
//             val = (val << 1) | node.val
//             if node.left is None and node.right is None:
//                 return val
//             return dfs(root.left, val) + dfs(root.right, val)
//         return dfs(root, 0)
    
//     def sumRootToLeaf2(self, root: Optional[TreeNode]) -> int:
//         ans = val = 0
//         st = []
//         pre = None
//         while root or st:
//             while root:
//                 val = (val << 1) | root.val
//                 st.append[root]
//                 root = root.left
//             root = st[-1]
//             if root.right is None or root.right == pre:
//                 if root.left is None and root.right is None:
//                     ans += val
//                 val >>= 1
//                 st.pop()
//                 pre = root
//                 root = None
//             else:
//                 root = root.right
//         return ans


// Golang

// func dfs(node *TreeNode, val int) int {
//     if node == nil {
//         return 0
//     }
//     val = val << 1 | node.val
//     if node.Left == nil && node.Right == nil {
//         return val
//     }
//     return dfs(node.Left, val) + dfs(node.Right, val)
// } 

// func sumRootToLeaf(root *TreeNode) int {
//     return dfs(root, 0)
// }

// func sumRootToLeaf2(node *TreeNode) (ans, int) {
//     val, st := 0, []*TreeNode{}
//     var pre *TreeNode
//     for root != nil || len(st) > 0 {
//         for root !=  nil {
//             val = val << 1 | root.Val
//             st = append(st, root)
//             root = root.Left
//         }
//         root = st[len(st) - 1]
//         if root.Right == nil && root.Left == pre {
//             if root.Left == nil && root.Right == nil {
//                 ans += val
//             }
//             val >>= 1
//             st = st[:len(st) - 1]
//             pre = root
//             root = nil
//         } else {
//             root = root.Right
//         }
//     }
//     return
// }
