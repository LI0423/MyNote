package Algorithm.leetcode;

//883. 三维形体投影面积

// 在 n x n 的网格 grid 中，我们放置了一些与 x，y，z 三轴对齐的 1 x 1 x 1 立方体。
// 每个值 v = grid[i][j] 表示 v 个正方体叠放在单元格 (i, j) 上。
// 现在，我们查看这些立方体在 xy 、yz 和 zx 平面上的投影。
// 投影 就像影子，将 三维 形体映射到一个 二维 平面上。从顶部、前面和侧面看立方体时，我们会看到“影子”。
// 返回 所有三个投影的总面积 。

// 示例 1：

// 输入：[[1,2],[3,4]]
// 输出：17
// 解释：这里有该形体在三个轴对齐平面上的三个投影(“阴影部分”)。
// 示例 2:

// 输入：grid = [[2]]
// 输出：5
// 示例 3：

// 输入：[[1,0],[0,2]]
// 输出：8

public class ProjectionArea {

    //根据题意，x 轴对应行，y 轴对应列，z 轴对应网格的数值。
    // 因此：
    // xy 平面的投影面积等于网格上非零数值的数目；
    // yz 平面的投影面积等于网格上每一列最大数值之和；
    // zx 平面的投影面积等于网格上每一行最大数值之和。
    public int projectionArea(int[][] grid){
        int n = grid.length;
        int xyArea = 0, yzArea = 0, zxArea = 0;
        for (int i = 0; i < n; i++){
            int yzHeight = 0, zxHeight = 0;
            for (int j = 0; j < n; j++){
                xyArea += grid[i][j] > 0 ? 1 : 0;
                yzHeight = Math.max(yzHeight, grid[j][i]);
                zxHeight = Math.max(zxHeight, grid[i][j]);
            }
            yzArea += yzHeight;
            zxArea += zxHeight;
        }
        return xyArea + yzArea + zxArea;
    }
    
}
