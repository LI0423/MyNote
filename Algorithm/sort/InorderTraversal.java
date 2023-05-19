package Algorithm.sort;

import java.util.ArrayList;
import java.util.List;

import Algorithm.leetcode.TreeNode;

public class InorderTraversal {
    
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
