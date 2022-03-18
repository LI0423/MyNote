package Algorithm.leetcode;

public class IsSymmetricTree {

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
