package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

//637. 二叉树的层平均值

// 给定一个非空二叉树的根节点 root , 以数组的形式返回每一层节点的平均值。与实际答案相差 10-5 以内的答案可以被接受。

// 示例 1：

// 输入：root = [3,9,20,null,null,15,7]
// 输出：[3.00000,14.50000,11.00000]
// 解释：第 0 层的平均值为 3,第 1 层的平均值为 14.5,第 2 层的平均值为 11 。
// 因此返回 [3, 14.5, 11] 。
// 示例 2:

// 输入：root = [3,9,20,15,7]
// 输出：[3.00000,14.50000,11.00000]

public class AverageOfLevels {

    //使用深度优先搜索计算二叉树的层平均值，需要维护两个数组，counts 用于存储二叉树的每一层的节点数，sums 用于存储二叉树的每一层的节点值之和。
    //搜索过程中需要记录当前节点所在层，如果访问到的节点在第 i 层，则将 counts[i] 的值加 1，并将该节点的值加到 sums[i]。
    //遍历结束之后，第 i 层的平均值即为 sums[i] /counts[i]。
    public List<Double> averageOfLevels(TreeNode root) {
        List<Integer> counts = new ArrayList<>();
        List<Double> sums = new ArrayList<>();
        dfs(root, 0, counts, sums);
        List<Double> averages = new ArrayList<Double>();
        for(int i = 0; i < sums.size(); i++){
            averages.add(sums.get(i) / counts.get(i));
        }
        return averages;

    }

    public void dfs(TreeNode root, int level, List<Integer> counts, List<Double> sums) {
        if(root == null) {
            return;
        }
        if(level < sums.size()) {
            sums.set(level, sums.get(level) + root.val);
            counts.set(level, counts.get(level) + 1);
        } else {
            sums.add(1.0 * root.val);
            counts.add(1);
        }
        dfs(root.left, level+1, counts, sums);
        dfs(root.right, level+1, counts, sums);
    }
}
