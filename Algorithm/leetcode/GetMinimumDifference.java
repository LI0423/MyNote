package Algorithm.leetcode;

//530. 二叉搜索树的最小绝对差
// 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
// 差值是一个正数，其数值等于两值之差的绝对值。

// 示例 1：

// 输入：root = [4,2,6,1,3]
// 输出：1
// 示例 2：

// 输入：root = [1,0,48,null,null,12,49]
// 输出：1

public class GetMinimumDifference {
  
  int pre;
  int ans;
  public int getMinimumDifference(TreeNode root) {
    ans = Integer.MAX_VALUE;
    inOrder(root);
    return ans; 
  }

  public void inOrder(TreeNode root){
    if (root == null) {
        return;
    }
    inOrder(root.left);
    if(pre == -1) {
        pre = root.val;
    } else {
        ans = Math.min(ans, root.val - pre);
        pre = root.val;
    }
    inOrder(root.right);
  }

}
