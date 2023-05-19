package Algorithm.leetcode;

//566. 重塑矩阵

// 在 MATLAB 中，有一个非常有用的函数 reshape ，它可以将一个 m x n 矩阵重塑为另一个大小不同（r x c）的新矩阵，但保留其原始数据。
// 给你一个由二维数组 mat 表示的 m x n 矩阵，以及两个正整数 r 和 c ，分别表示想要的重构的矩阵的行数和列数。
// 重构后的矩阵需要将原始矩阵的所有元素以相同的 行遍历顺序 填充。
// 如果具有给定参数的 reshape 操作是可行且合理的，则输出新的重塑矩阵；否则，输出原始矩阵。

// 示例 1：

// 输入：mat = [[1,2],[3,4]], r = 1, c = 4
// 输出：[[1,2,3,4]]
// 示例 2：

// 输入：mat = [[1,2],[3,4]], r = 2, c = 4
// 输出：[[1,2],[3,4]]






















public class MatrixReshape {

    public int[][] matrixReshape(int[][] mat, int r, int c){
        if(mat == null) {
            return null;
        }
        int length = mat.length * mat[0].length;
        int[] tmp = new int[length];
        if (length / c == r) {
            int[][] res =new int[r][c];
            int t = 0;
            for(int i = 0; i < mat.length; i++){
                for(int j = 0; j < mat[0].length; j++){
                    tmp[t] = mat[i][j];
                    t++;
                }
            }
            int s = 0;
            for(int i = 0; i < r; i++){
                for(int j = 0; j < c; j++){
                    if(s <= tmp.length){
                        res[i][j] = tmp[s];
                        s++;
                    } else {
                        break;
                    }
                }
            }
            return res;
        }
        return mat;
    }

    public static void main(String[] args) {
        MatrixReshape matrixReshape = new MatrixReshape();
        int[][] mat = new int[][]{{1,2},{3,4}};
        matrixReshape.matrixReshape(mat, 1, 4);
    }
}
