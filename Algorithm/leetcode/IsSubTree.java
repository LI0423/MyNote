package Algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

//572. 另一棵树的子树
// 给你两棵二叉树 root 和 subRoot 。检验 root 中是否包含和 subRoot 具有相同结构和节点值的子树。如果存在，返回 true ；否则，返回 false 。
// 二叉树 tree 的一棵子树包括 tree 的某个节点和这个节点的所有后代节点。tree 也可以看做它自身的一棵子树。

// 示例 1：

// 输入：root = [3,4,5,1,2], subRoot = [4,1,2]
// 输出：true
// 示例 2：

// 输入：root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
// 输出：false























public class IsSubTree {

    //深度优先搜索
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        return dfs(root, subRoot);
    }

    public boolean dfs(TreeNode s, TreeNode t) {
        if (s == null) {
            return false;
        }
        return check(s, t) || dfs(s.left, t) || dfs(s.right, t);
    }

    public boolean check(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null || s.val != t.val) {
            return false;
        }
        return check(s.left, t.left) && check(s.right, t.right);
    }

    /**
     *  把每个子树都映射成一个唯一的数，如果 t 对应的数字和 s 中任意一个子树映射的数字相等，则 t 是 s 的某一棵子树。如何映射呢？我们可以定义这样的哈希函数：
        fo=vo+31⋅fl⋅p(sl)+179⋅fr⋅p(sr) 
        这里 fx 表示节点 xxx 的哈希值，sx 表示节点 x 对应的子树大小，vx 代表节点 x 的 val，p(n) 表示第 n 个素数，o 表示当前节点，l 和 r 分别表示左右孩子。
        这个式子的意思是：当前节点 o 的哈希值等于这个点的 val 加上 31 倍左子树的哈希值乘以第 sl 个素数，再加上 179 倍右子树的哈希值乘以第 sr 个素数。
        这里的 31 和 179 这两个数字只是为了区分左右子树，你可以自己选择你喜欢的权值。
     */
    static final int MAX_N = 1005;
    static final int MOD = 1000000007;
    boolean[] vis = new boolean[MAX_N];
    int[] p = new int[MAX_N];
    int tot;
    Map<TreeNode, int[]> hS = new HashMap<>();
    Map<TreeNode, int[]> hT = new HashMap<>();

    public boolean isSubTree(TreeNode root, TreeNode subRoot){
        getPrime();
        dfs(root, hS);
        dfs(subRoot, hT);

        int tHash = hT.get(subRoot)[0];
        for(Map.Entry<TreeNode, int[]> entry : hS.entrySet()){
            if(entry.getValue()[0] == tHash){
                return true;
            }
        }
        return false;
    }

    public void getPrime(){
        vis[0] = vis[1] = true;
        tot = 0;
        for(int i = 2; i < MAX_N; ++i){
            if(!vis[i]){
                p[++tot] = i;
            }
            for(int j = 1; j < tot && i * p[j] < MAX_N; ++j ){
                vis[i * p[j]] = true;
                if(i % p[j] == 0){
                    break;
                }
            }
        }
    }

    void dfs(TreeNode root, Map<TreeNode, int[]> h){
        h.put(root, new int[]{root.val, 1});
        if(root.left == null || root.right == null){
            return ;
        }
        if(root.left != null) {
            dfs(root.left, h);
            int[] val = h.get(root);
            val[1] += h.get(root.left)[1];
            val[0] = (int) ((val[0] + (31L * h.get(root.left)[0] * p[h.get(root.left)[1]]) % MOD) % MOD);
        }
        if(root.right != null) {
            dfs(root.right, h);
            int[] val = h.get(root);
            val[1] += h.get(root.right)[1];
            val[0] = (int) ((val[0] + (179L * h.get(root.right)[0] * p[h.get(root.right)[1]]) % MOD) % MOD);
        }
    }
}
