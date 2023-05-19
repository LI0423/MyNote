package Algorithm.sort;

import java.util.ArrayList;
import java.util.List;

import Algorithm.leetcode.TreeNode;

public class PostorderTraversal {
    
    private static List<Integer> ans;
    public List<Integer> postorderTraversal(TreeNode root){
        ans = new ArrayList<>();
        dfs(root);
        return ans;
    }

    public void dfs(TreeNode root){
        if(root == null){
            return;
        }
        dfs(root.left);
        dfs(root.right);
        ans.add(root.val);
    }

    
}
