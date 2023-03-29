package Algorithm.leetcode;

//461. 汉明距离
// 两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
// 给你两个整数 x 和 y，计算并返回它们之间的汉明距离。

// 示例 1：

// 输入：x = 1, y = 4
// 输出：2
// 解释：
// 1   (0 0 0 1)
// 4   (0 1 0 0)
//        ↑   ↑
// 上面的箭头指出了对应二进制位不同的位置。
// 示例 2：

// 输入：x = 3, y = 1
// 输出：1

public class HammingDistance {

    // 具体地，记 s=x⊕y，我们可以不断地检查 s 的最低位，如果最低位为 1，那么令计数器加一，
    // 然后我们令 s 整体右移一位，这样 s 的最低位将被舍去，原本的次低位就变成了新的最低位。
    // 我们重复这个过程直到 s=0 为止。这样计数器中就累计了 s 的二进制表示中 1 的数量。
    public int hammingDisatnce(int x, int y){
        int s = x ^ y, ret = 0;
        while (s != 0) {
            ret += s & 1;
            s >>= 1;
        }
        return ret;
    }
}


//Golang

// func hammingDistance(x, y int) (ans int) {
//     for s := x ^ y; s > 0; s >>= 1 {
//         ans += s & 1
//     }
//     return
// }
