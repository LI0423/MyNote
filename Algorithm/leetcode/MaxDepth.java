package Algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

//559. N 叉树的最大深度

// 给定一个 N 叉树，找到其最大深度。
// 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。
// N 叉树输入按层序遍历序列化表示，每组子节点由空值分隔（请参见示例）。

// 示例 1：

// 输入：root = [1,null,3,2,4,null,5,6]
// 输出：3
// 示例 2：

// 输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
// 输出：5

public class MaxDepth {

    class Node {
        public int val;
        public List<Node> children;
    
        public Node() {}
    
        public Node(int _val) {
            val = _val;
        }
    
        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    //DFS: 从 root 的所有子节点中的取最大深度，并在此基础上加一（统计 root 节点）即是答案。
    public int maxDepth(Node root){
        if(root == null) {
            return 0;
        }
        int ans = 0;
        for(Node node : root.children){
            ans = Math.max(ans, maxDepth(node));
        }
        return ans + 1;
    }

    //BFS: 其本质是对多叉树进行层序处理，当 BFS 过程结束，意味着达到最大层数（深度），所记录的最大层数（深度）即是答案。
    public int maxDepth2(Node root){
        if (root == null) {
            return 0;
        }
        int ans = 0;
        Deque<Node> queue = new ArrayDeque<>();
        queue.addLast(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size-- > 0) {
                Node t = queue.pollFirst();
                for(Node node : t.children) {
                    queue.addLast(node);
                }
            }
            ans++;
        }
        return ans;
    }
}
