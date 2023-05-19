package Algorithm.sort;

import java.util.ArrayList;
import java.util.List;

import Algorithm.leetcode.Node;

//589. N 叉树的前序遍历

// 给定一个 n 叉树的根节点  root ，返回 其节点值的 前序遍历 。
// n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。

// 示例 1：

// 输入：root = [1,null,3,2,4,null,5,6]
// 输出：[1,3,5,6,2,4]
// 示例 2：

// 输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
// 输出：[1,2,3,6,7,11,14,4,8,12,5,9,13,10]

public class NPreorder {

    public List<Integer> preorder(Node root){
       List<Integer> list = new ArrayList<>();
       dfs(root, list);
       return list;
    }

    public void dfs(Node root, List<Integer> res){
        if(root == null) {
            return;
        }
        res.add(root.val);
        for(Node ch : root.children){
            dfs(ch, res);
        }
    }
}
