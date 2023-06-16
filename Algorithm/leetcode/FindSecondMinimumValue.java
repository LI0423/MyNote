package Algorithm.leetcode;

//671. 二叉树中第二小的节点

// 给定一个非空特殊的二叉树，每个节点都是正数，并且每个节点的子节点数量只能为 2 或 0。如果一个节点有两个子节点的话，那么该节点的值等于两个子节点中较小的一个。
// 更正式地说，即 root.val = min(root.left.val, root.right.val) 总成立。
// 给出这样的一个二叉树，你需要输出所有节点中的 第二小的值 。
// 如果第二小的值不存在的话，输出 -1 。

// 示例 1：

// 输入：root = [2,2,5,null,null,5,7]
// 输出：5
// 解释：最小的值是 2 ，第二小的值是 5 。
// 示例 2：

// 输入：root = [2,2,2]
// 输出：-1
// 解释：最小的值是 2, 但是不存在第二小的值。

public class FindSecondMinimumValue {
    //使用深度优先搜索的方法对二叉树进行遍历。
    //假设当前遍历到的节点为 node，如果 node 的值严格大于 rootvalue，那么我们就可以用 node 的值来更新答案 ans。当我们遍历完整棵二叉树后，即可返回 ans。
    int ans;
    int rootvalue;
    public int findSecondMinimumValue(TreeNode root){
        ans = -1;
        rootvalue = root.val;
        dfs(root);
        return ans;
    }

    void dfs(TreeNode root){
        if(root == null){
            return;
        }
        if(ans != -1 && root.val >= ans){
            return;
        }
        if(root.val > rootvalue){
            ans = root.val;
        }
        dfs(root.left);
        dfs(root.right);
    }
    
}
