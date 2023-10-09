package Algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

//938. 二叉搜索树的范围和

// 给定二叉搜索树的根结点 root，返回值位于范围 [low, high] 之间的所有结点的值的和。

// 示例 1：

// 输入：root = [10,5,15,3,7,null,18], low = 7, high = 15
// 输出：32
// 示例 2：

// 输入：root = [10,5,15,3,7,13,18,1,null,6], low = 6, high = 10
// 输出：23

public class RangeSumBST {

    // 深度优先遍历
    public int rangeSumBST(TreeNode root, int low, int high){
        if (root == null) {
            return 0;
        }
        if (root.val > high) {
            return rangeSumBST(root.left, low, high);
        }
        if (root.val < low) {
            return rangeSumBST(root.right, low, high);
        }
        return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
    }

    // 广度优先遍历
    public int rangeSumBST2(TreeNode root, int low, int high) {
        int sum = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                continue;
            }
            if (node.val > high) {
                queue.offer(node.left);
            }
            if (node.val < low) {
                queue.offer(node.right);
            } else {
                sum += node.val;
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        return sum;
    }
}


// Python

// class RangeSumBST():
//     def rangeSumBST(self, root: TreeNode, low int, high int) -> int:
//         if not root:
//             return 0
//         if root.val > high:
//             return rangeSumBST(root.left, low, high)
//         if root.val < low:
//             return rangeSumBST(root.right, low, high)
//         return root.val + self.rangeSumBST(root.left, low, high) + self.rangeSumBST(root.right, low, high)

//     def rangeSumBST(self, root: TreeNode, low int, high int) -> int:
//         total = 0
//         q = collections.deque([root])
//         while q :
//             node = q.popleft()
//             if not node :
//                 continue
//             elif node.val > high :
//                 q.append(q.left)
//             elif node.val < low :
//                 q.append(q.right)
//             else :
//                 total += node.val
//                 q.append(node.left)
//                 q.append(node.right)
//         return total


// Golang

// func rangeSumBST(root *TreeNode, low, high int) int {
//     if root == nil {
//         return 0
//     }
//     if root.Val > high {
//         return rangeSumBST(root.left, low, high)
//     }
//     if root.Val < low {
//         return rangeSumBST(root.right, low, high)
//     }
//     return root.Val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high)
// }

// func rangeSumBST(root *TreeNode, low, high int) int {
//     q := []*TreeNode{root}
//     for len(q) > 0 {
//         node := q[0]
//         q = q[1:]
//         if node == nil {
//             continue
//         }
//         if root.Val > high {
//             q = append(q, node.left)
//         } else if root.Val < low {
//             q = append(q, node.right)
//         } else {
//             sum += node.Val
//             q = append(q, node.left, node.right)
//         }
//     }
//     return
// }