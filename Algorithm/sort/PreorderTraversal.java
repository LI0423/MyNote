package Algorithm.sort;

import java.util.ArrayList;
import java.util.List;

import Algorithm.leetcode.TreeNode;

public class PreorderTraversal{
    
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