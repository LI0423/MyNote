package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

//118. 杨辉三角

// 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。

// 在「杨辉三角」中，每个数是它左上方和右上方的数的和。

// 示例 1:

// 输入: numRows = 5
// 输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
// 示例 2:

// 输入: numRows = 1
// 输出: [[1]]

public class YHTriangle {
    public List<List<Integer>> generate(int numRows){
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for(int i = 0; i < numRows; ++i){
            List<Integer> list = new ArrayList<>();
            for(int j = 0; j <= i; ++j){
                if(j == 0 || j == i){
                    list.add(0);
                } else {
                    list.add(res.get(i - 1).get(j-1) + res.get(i - 1).get(j));
                }
            }
            res.add(list);
        }
        return res;
    }
}


// Python

// class YHTriangle:
//     def generate(self, numRows: int):
//         yh = [[]] * numRows
//         for row in range(len(yh)):
//             yh[row] = [None] * (row + 1)
//             for col in range(len(yh[row])):
//                 if col == 0 or col == row:
//                     yh[row][col] = 1
//                 else:
//                     yh[row][col] = yh[row - 1][col] + yh[row - 1][col - 1]


//Golang

// func generate(numRows int) [][]int {
//     ans := make([][]int, numRws)
//     for i := range ans {
//         ans[i] := make([]int, i+1)
//         ans[i][0] = 1
//         ans[i][i] = 1
//         for j := 1; j < i; j++ {
//             ans[i][j] = ans[i-1][j] + ans[i-1][j-1]
//         }
//     }
//     return res
// }