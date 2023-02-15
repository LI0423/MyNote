package Algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

//202. 快乐数

// 编写一个算法来判断一个数 n 是不是快乐数。
// 「快乐数」 定义为：
// 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
// 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
// 如果这个过程 结果为 1，那么这个数就是快乐数。
// 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。

// 示例 1：

// 输入：n = 19
// 输出：true
// 解释：
// 12 + 92 = 82
// 82 + 22 = 68
// 62 + 82 = 100
// 12 + 02 + 02 = 1
// 示例 2：

// 输入：n = 2
// 输出：false

public class IsHappy {

    public boolean isHappy(int n){
        Set<Integer> set = new HashSet<>();
        while(n != 1 && !set.contains(n)){
            set.add(n);
            n = getNext(n);
        }
        return n == 1;
    }

    public boolean isHappy2(int n){
        int slow = n;
        int fast = getNext(n);
        while(n != 1 && slow != fast){
            slow = getNext(n);
            fast = getNext(getNext(n));
        }
        return fast == 1;
    }

    public int getNext(int n){
        int sum = 0;
        while (n > 0){
            int d = sum % 10;
            n = n /10;
            sum += d * d;
        }
        return sum;
    }
    
}

//Golang

// func isHappy(n int) bool {
//     set := map[int]bool{}
//     for ; n != 1 && !set[n]; n, set[n] = getNext(n), true {}
//     return n == 1
// }

// func getNext(n int) int {
//     sum := 0
//     for n > 0 {
//         sum += (n%10) * (n%10)
//         n = n / 10
//     }
//     return sum
// }
