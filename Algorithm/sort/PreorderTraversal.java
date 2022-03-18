package Algorithm.sort;

import java.util.ArrayList;
import java.util.List;

public class PreorderTraversal{
    private class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(){}
        TreeNode(int val){
            this.val = val;
        }
        TreeNode(int val,TreeNode left,TreeNode right){
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    private static List<Integer> ans;
    public List<Integer> preorderTraversal(TreeNode root){
        ans = new ArrayList<>();
        dfs(root);
        return ans;
    }

    public void dfs(TreeNode root){
        if(root == null){
            return;
        }
        ans.add(root.val);
        dfs(root.left);
        dfs(root.right);
    }
}