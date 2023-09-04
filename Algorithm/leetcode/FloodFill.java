package Algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

//733. 图像渲染
// 有一幅以 m x n 的二维整数数组表示的图画 image ，其中 image[i][j] 表示该图画的像素值大小。
// 你也被给予三个整数 sr ,  sc 和 newColor 。你应该从像素 image[sr][sc] 开始对图像进行 上色填充 。
// 为了完成 上色工作 ，从初始像素开始，记录初始坐标的 上下左右四个方向上 像素值与初始坐标相同的相连像素点，
// 接着再记录这四个方向上符合条件的像素点与他们对应 四个方向上 像素值与初始坐标相同的相连像素点，……，重复该过程。
// 将所有有记录的像素点的颜色值改为 newColor 。 最后返回 经过上色渲染后的图像 。 

// 示例 1:

// 输入: image = [[1,1,1],[1,1,0],[1,0,1]]，sr = 1, sc = 1, newColor = 2
// 输出: [[2,2,2],[2,2,0],[2,0,1]]
// 解析: 在图像的正中间，(坐标(sr,sc)=(1,1)),在路径上所有符合条件的像素点的颜色都被更改成2。
// 注意，右下角的像素没有更改为2，因为它不是在上下左右四个方向上与初始点相连的像素点。
// 示例 2:

// 输入: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, newColor = 2
// 输出: [[2,2,2],[2,2,2]]

public class FloodFill {
    int[] dx = {1, 0, 0, -1};
    int[] dy = {0, 1, -1, 0};

    //广度优先遍历
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int currColor = image[sr][sc];
        if (currColor == color){
            return image;
        }
        int n = image.length, m = image[0].length;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{sr, sc});
        image[sr][sc] = color;
        while(!queue.isEmpty()){
            int[] cell = queue.poll();
            int x = cell[0], y = cell[1];
            for (int i = 0; i < 4; i++){
                int mx = x + dx[i], my = y + dy[i];
                if (mx >= 0 && mx < n && my >= 0 && my < m && image[mx][my] == currColor){
                    queue.offer(new int[]{mx, my});
                    image[mx][my] = color;
                }
            }
        }
        return image;
    }

    //深度优先遍历
    public int[][] floodFill2(int[][] image, int sr, int sc, int color){
        int currColor = image[sr][sc];
        if(currColor != color){
            dfs(image, sc, color, currColor, currColor);
        }
        return image;
    }

    public void dfs(int[][] image, int x, int y, int currColor, int color){
        if(image[x][y] == currColor){
            image[x][y] = color;
            for (int i = 0; i < 4; i++){
                int mx = x + dx[i], my = y + dy[i];
                if (mx >= 0 && mx < image.length && my >= 0 && my < image[0].length){
                    dfs(image, mx, my, currColor, color);
                }
            }
        }
    }
}