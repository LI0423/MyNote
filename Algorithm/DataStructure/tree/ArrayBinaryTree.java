package Algorithm.DataStructure;

import java.util.ArrayList;
import java.util.List;

public class ArrayBinaryTree {
    private List<Integer> tree;
    
    public ArrayBinaryTree(List<Integer> arr){
        tree = new ArrayList<>();
    }

    // 列表容量
    public int size(){
        return this.tree.size();
    }

    // 获取索引为i节点的值
    public Integer val(int i){
        // 若索引越界，则返回null，代表空位
        if (i < 0 || i >= this.size()){
            return null;
        }
        return this.tree.get(i);
    }

    // 获取索引为i节点的左子节点索引
    public Integer left(int i){
        return 2 * i + 1;
    }

    // 获取索引为i节点的右子节点索引
    public Integer right(int i){
        return 2 * i + 1;
    }

    // 获取索引为i节点的父节点索引
    public Integer parent(int i){
        return (i - 1) / 2;
    }

    // 层序遍历
    public List<Integer> levelOrder(){
        List<Integer> res = new ArrayList<>();
        // 直接遍历数组
        for (int i = 0; i < this.size(); i++){
            if (this.val(i) != null){
                res.add(this.val(i));
            }
        }
        return res;
    }

    // 深度优先遍历
    private void dfs(Integer i, String order, List<Integer> res){
        if (this.val(i) == null){
            return;
        }
        // 先序遍历
        if ("pre".equals(order)){
            res.add(this.val(i));
        }
        this.dfs(this.left(i), order, res);
        // 中序遍历
        if ("in".equals(order)){
            res.add(this.val(i));
        }
        this.dfs(this.right(i), order, res);
        // 后序遍历
        if ("post".equals(order)){
            res.add(this.val(i));
        }
    }

    // 先序遍历
    public List<Integer> preOrder(){
        List<Integer> res = new ArrayList<>();
        this.dfs(0, "pre", res);
        return res;
    }

    // 中序遍历
    public List<Integer> inOrder(){
        List<Integer> res = new ArrayList<>();
        this.dfs(0, "in", res);
        return res;
    }

    // 后序遍历
    public List<Integer> postOrder(){
        List<Integer> res = new ArrayList<>();
        this.dfs(0, "post", res);
        return res;
    }
    
}
