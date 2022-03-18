package Algorithm.sort;

import java.util.ArrayList;
import java.util.List;

public class InorderTraversal {

    private static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(){}
        TreeNode(int val){
            this.val = val;
        }
        TreeNode(int val , TreeNode left,TreeNode right){
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    private static List<Integer> ans;
    public List<Integer> inorderTraversal(TreeNode root){
        ans = new ArrayList<>();
        dfs(root);
        return ans;
    }

    public void dfs(TreeNode root){
        if(root == null){
            return;
        }
        dfs(root.left);
        ans.add(root.val);
        dfs(root.right);
    }

}
