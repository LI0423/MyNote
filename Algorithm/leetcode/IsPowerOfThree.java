package Algorithm.leetcode;

// 326. 3 的幂

// 给定一个整数，写一个函数来判断它是否是 3 的幂次方。如果是，返回 true ；否则，返回 false 。
// 整数 n 是 3 的幂次方需满足：存在整数 x 使得 n == 3x

// 示例 1：

// 输入：n = 27
// 输出：true
// 示例 2：

// 输入：n = 0
// 输出：false
// 示例 3：

// 输入：n = 9
// 输出：true
// 示例 4：

// 输入：n = 45
// 输出：false

public class IsPowerOfThree {
    public boolean isPowerOfThree(int n){
        if (n <= 0) {
            return false;
        }
        while (n % 3 == 0){
            n /= 3;
        }
        return n == 1;
    }
}

//Golang

// func isPowerOfThree(n int) bool {
//     if n <= 0 {
//         return false;
//     }
//     for n % 3 == 0 {
//         n /= 3
//     }
//     return n == 1;
// }
