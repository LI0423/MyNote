package Algorithm.leetcode;

public class IsSymmetricTree {

    public boolean isSymmetricTree(TreeNode root){
        
        return dfs(root, root);

    }

    public boolean dfs(TreeNode root1, TreeNode root2){
        if(root1 == null && root2 ==null){
            return true;
        }
        if(root1 == null || root2 == null || root1.val != root2.val){
            return false;
        }
        return dfs(root1.left, root2.right) && dfs(root1.right, root2.left);
    }
    
}
