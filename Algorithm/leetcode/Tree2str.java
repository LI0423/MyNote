package Algorithm.leetcode;

import java.util.List;

//606. 根据二叉树创建字符串

// 给你二叉树的根节点 root ，请你采用前序遍历的方式，将二叉树转化为一个由括号和整数组成的字符串，返回构造出的字符串。
// 空节点使用一对空括号对 "()" 表示，转化后需要省略所有不影响字符串与原始二叉树之间的一对一映射关系的空括号对。

// 示例 1：

// 输入：root = [1,2,3,4]
// 输出："1(2(4))(3)"
// 解释：初步转化后得到 "1(2(4)())(3()())" ，但省略所有不必要的空括号对后，字符串应该是"1(2(4))(3)" 。
// 示例 2：

// 输入：root = [1,2,3,null,4]
// 输出："1(2()(4))(3)"
// 解释：和第一个示例类似，但是无法省略第一个空括号对，否则会破坏输入与输出一一映射的关系。

public class Tree2str {
    
    // 生成字符串的规则其实就是在「前序遍历」输出节点值的同时，在每颗子树的左右添加一对 ()（根节点除外），同时需要忽略掉一些不必要的 () 。
    // 所谓的不必要就是指当以某个节点 xxx 为根时，其只「有左子树」而「没有右子树」时，右子树的 () 可被忽略，或者「左右子树都没有」时，两者的 () 可被忽略。
    // 或者反过来说，如果对于每个非空节点才添加 () 的话，那么当「有右子树」同时「没有左子树」时，左子树的 () 不能被忽略，需要额外添加，从而确保生成出来的字符串能够与「有左子树」同时「没有右子树」的情况区分开来，而不会产生二义性。
    StringBuilder sb = new StringBuilder();
    public String tree2str(TreeNode root){
        dfs(root);
        return sb.substring(1, sb.length() - 1);
    }

    void dfs(TreeNode root){
        sb.append("(");
        sb.append(root.val);
        if(root.left != null) dfs(root.left);
        else if (root.right != null) sb.append("()");
        if(root.right != null) dfs(root.right);
        sb.append(")");
    }
}
