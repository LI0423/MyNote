package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

//872. 叶子相似的树

// 请考虑一棵二叉树上所有的叶子，这些叶子的值按从左到右的顺序排列形成一个 叶值序列 。
// 举个例子，如上图所示，给定一棵叶值序列为 (6, 7, 4, 9, 8) 的树。
// 如果有两棵二叉树的叶值序列是相同，那么我们就认为它们是 叶相似 的。
// 如果给定的两个根结点分别为 root1 和 root2 的树是叶相似的，则返回 true；否则返回 false 。

// 示例 1：

// 输入：root1 = [3,5,1,6,2,9,8,null,null,7,4], root2 = [3,5,1,6,7,4,2,null,null,null,null,null,null,9,8]
// 输出：true
// 示例 2：

// 输入：root1 = [1,2,3], root2 = [1,3,2]
// 输出：false

public class LeafSimilar {

    public boolean leafSimilar(TreeNode root1, TreeNode root2){
        List<Integer> list1 = new ArrayList<>();
        if (root1 != null ){
            dfs(root1, list1);
        }

        List<Integer> list2 = new ArrayList<>();
        if (root2 != null){
            dfs(root2, list2);
        }
        return list1.equals(list2);
    }

    public void dfs(TreeNode root, List<Integer> list){
        if (root.left == null && root.right == null){
            list.add(root.val);
        } else {
            if (root.left != null){
                dfs(root.left, list);
            } 
            if (root.right != null){
                dfs(root.right, list);
            }
        }
    }
    
}


//Python

// class LeafSimilar:
//     def leafSimilar(self, root1: TreeNode, root2: TreeNode) -> bool:
//         def dfs(root: TreeNode):
//             if not root.left and not root.right:
//                 yield root.val
//             else:
//                 if root.left:
//                     yield from dfs(root.left)
//                 if root.right:
//                     yield from dfs(root.right)
//         list1 = list(dfs(root1)) if root1 else list()
//         list2 = list(dfs(root2)) if root2 else list()
//         return list1 == list2


//Golang

// func leafSimilar(root1, root2 *TreeNode) bool {
//     vals := []int{}
//     var dfs func(*TreeNode)
//     dfs = func(node * TreeNode){
//         if node == nil {
//             return
//         }
//         if node.left == nil && node.right == nil {
//             vals = append(vals, node.vals)
//             return
//         }
//         dfs(node.left)
//         dfs(node.right)
//     }
//     dfs(root1)
//     vals1 := append([]int(nil), vals...)
//     vals = []int{}
//     dfs(root2)
//     if len(vals) != len(vals1){
//         return false
//     }
//     for i, v := range vals1 {
//         if v != vals[i] {
//             return false
//         }
//     }
//     return true
// } 
        
