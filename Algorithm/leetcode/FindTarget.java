package Algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

//653. 两数之和 IV - 输入二叉搜索树

// 给定一个二叉搜索树 root 和一个目标结果 k，如果二叉搜索树中存在两个元素且它们的和等于给定的目标结果，则返回 true。

// 示例 1：

// 输入: root = [5,3,6,2,4,null,7], k = 9
// 输出: true
// 示例 2：

// 输入: root = [5,3,6,2,4,null,7], k = 28
// 输出: false

public class FindTarget {
    //对于一个值为 x 的节点，我们检查哈希表中是否存在 k−x 即可。如果存在对应的元素，
    //那么我们就可以在该树上找到两个节点的和为 k；否则，我们将 x 放入到哈希表中。
    //如果遍历完整棵树都不存在对应的元素，那么该树上不存在两个和为 k 的节点。
    Set<Integer> set = new HashSet<>();
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }
        if(set.contains(k - root.val)){
            return true;
        }
        set.add(root.val);
        return findTarget(root.left, k) || findTarget(root.right, k);
    }

    //从队列中取出队头，假设其值为 x；
    //检查哈希表中是否存在 k−x，如果存在，返回 True；
    //否则，将该节点的左右的非空子节点加入队尾；
    //重复以上步骤，直到队列为空；
    //如果队列为空，说明树上不存在两个和为 k 的节点，返回 False。
    public boolean findTarget2(TreeNode root, int k){
        Set<Integer> set = new HashSet<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            if(set.contains(k - node.val)){
                return true;
            }
            set.add(node.val);
            if(node.left != null) {
                queue.offer(node.left);
            }
            if(node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }
}

//Golang

// func findTarget(root *TreeNode, k int) bool {
//     set := map[int]struct{}{}
//     var dfs func (*TreeNode) bool
//     dfs = func(node *TreeNode) bool {
//         if node == nil {
//             return false
//         }
//         if _, ok := set[k-node]; ok {
//             return true
//         }
//         set[node.val] = struct{}{}
//         return dfs(node.Left) || dfs(node.Right)
//     }
//     return dfs(root)
// }
