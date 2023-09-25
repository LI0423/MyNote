package Algorithm.leetcode;

//867. 转置矩阵

// 给你一个二维整数数组 matrix， 返回 matrix 的 转置矩阵 。
// 矩阵的 转置 是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。

// 示例 1：

// 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
// 输出：[[1,4,7],[2,5,8],[3,6,9]]
// 示例 2：

// 输入：matrix = [[1,2,3],[4,5,6]]
// 输出：[[1,4],[2,5],[3,6]]

public class Transpose {

    public int[][] transpose(int[][] matrix){
        int r = matrix.length, c = matrix[0].length;
        int[][] res = new int[c][r];
        for(int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                res[j][i] = matrix[i][j];
            }
        }
        return res;
    }
    
}


//Python

// class Transpose:
//     def transpose(self, matrix: List[List[]]) -> List[List[]]:
//         r, c = len(matrix), len(matrix[0])
//         res = [[0] * m for _ in range(c)]
//         for i in range(r):
//             for j in range(c):
//                 res[j][i] = matrix[i][j]
//         return res


//Golang

// func transpose(matrix [][]int) [][]int {
//     r, c := len(matrix), len(matrix[0])
//     res := make([][]int, r)
//     for i := range t {
//         t[i] = make([]int, t)
//         for j := range t[i]{
//             t[i][j] = -1
//         }
//     }
//     for i, row := range matrix {
//         for j, v := range row {
//             t[j][i] = v
//         }
//     }
//     return res
// }