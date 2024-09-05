package Algorithm.DataStructure.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphAdjMat{
    // 顶点列表，元素代表“顶点值”，索引代表“顶点索引”
    List<Integer> vertices;
    // 邻接矩阵，行列索引对应“顶点索引”
    List<List<Integer>> adjMat;

    // 构造方法
    public GraphAdjMat(int[] vertices, int[][] edges){
        this.vertices = new ArrayList<>();
        this.adjMat = new ArrayList<>();
        // 添加顶点
        for (int val : vertices){
            addVertes(val);
        }
        // 添加边，edges元素代表顶点索引，即对应vertices元素索引
        for (int[] e : edges){
            addEdge(e[0], e[1]);
        }
    }

    // 获取顶点
    public int size(){
        return vertices.size();
    }

    // 添加顶点
    public void addVertes(int val){
        int n = size();
        // 向顶点列表中添加新顶点的值
        vertices.add(val);
        // 在邻接矩阵中添加一行
        List<Integer> newRow = new ArrayList<>();
        for (int j = 0; j < n; j++){
            newRow.add(0);
        }
        adjMat.add(newRow);
        // 在邻接矩阵中添加一列
        for(List<Integer> row: adjMat){
            row.add(0);
        }
    }

    // 删除顶点
    public void removeVertex(int index){
        if(index >= size()){
            throw new IndexOutOfBoundsException();
        }
        // 在顶点列表中移除索引index的顶点
        vertices.remove(index);
        // 在邻接矩阵中删除索引index的行
        adjMat.remove(index);
        // 在邻接矩阵中删除index的列
        for(List<Integer> row: adjMat){
            row.remove(index);
        }
    }

    // 添加边
    public void addEdge(int i, int j){
        if (i < 0 || j < 0 || j >= size() || i >= size() || i == j){
            throw new IndexOutOfBoundsException();
        }
        // 在无向图中，邻接矩阵关于主对角线对称，即满足(i, j) == (j, i)
        adjMat.get(i).set(j, 1);
        adjMat.get(j).set(i, 1);
    }

    // 删除边
    public void removeEdge(int i, int j){
        if (i < 0 || j < 0 || i >= size() || j >= size() || i == j){
            throw new IndexOutOfBoundsException();
        }
        adjMat.get(i).set(j, 0);
        adjMat.get(j).set(i, 0);
    }
}