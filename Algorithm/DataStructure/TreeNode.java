package Algorithm.DataStructure;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val){
        this.val = val;
    }

    public static void main(String[] args) {
        // 初始化节点
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);

        // 构建节点间的引用
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;

        // 插入P节点
        TreeNode P = new TreeNode(0);
        n1.left = P;
        P.left = n2;

        // 删除
        n1.left = n2;
    }
}
