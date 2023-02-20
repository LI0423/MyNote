package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

//119. 杨辉三角 II

// 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
// 在「杨辉三角」中，每个数是它左上方和右上方的数的和。

// 示例 1:

// 输入: rowIndex = 3
// 输出: [1,3,3,1]
// 示例 2:

// 输入: rowIndex = 0
// 输出: [1]
// 示例 3:

// 输入: rowIndex = 1
// 输出: [1,1]

public class YHTriangleII {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> pre = new ArrayList<Integer>();
        for (int i = 0; i <= rowIndex; ++i) {
            List<Integer> cur = new ArrayList<Integer>();
            for (int j = 0; j <= i; ++j) {
                if (j == 0 || j == i) {
                    cur.add(1);
                } else {
                    cur.add(pre.get(j - 1) + pre.get(j));
                }
            }
            pre = cur;
        }
        return pre;
    }
}

//Golang

// func getRow(rowIndex int) []int {
//     var pre, cur []int
//     for i := 0; i <= rowIndex; i++ {
//         cur = make([]int, i+1)
//         cur[0], cur[i] = 1, 1
//         for j := 1; j < i; j++ {
//             cur[j] = pre[j-1] + pre[j]
//         }
//         pre = cur
//     }
//     return pre
// }

