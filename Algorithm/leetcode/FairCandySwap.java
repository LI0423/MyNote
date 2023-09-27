package Algorithm.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//888. 公平的糖果交换

// 爱丽丝和鲍勃拥有不同总数量的糖果。给你两个数组 aliceSizes 和 bobSizes ，aliceSizes[i] 是爱丽丝拥有的第 i 盒糖果中的糖果数量，bobSizes[j] 是鲍勃拥有的第 j 盒糖果中的糖果数量。
// 两人想要互相交换一盒糖果，这样在交换之后，他们就可以拥有相同总数量的糖果。一个人拥有的糖果总数量是他们每盒糖果数量的总和。
// 返回一个整数数组 answer，其中 answer[0] 是爱丽丝必须交换的糖果盒中的糖果的数目，answer[1] 是鲍勃必须交换的糖果盒中的糖果的数目。如果存在多个答案，你可以返回其中 任何一个 。题目测试用例保证存在与输入对应的答案。

// 示例 1：

// 输入：aliceSizes = [1,1], bobSizes = [2,2]
// 输出：[1,2]
// 示例 2：

// 输入：aliceSizes = [1,2], bobSizes = [2,3]
// 输出：[1,2]
// 示例 3：

// 输入：aliceSizes = [2], bobSizes = [1,3]
// 输出：[2,3]
// 示例 4：

// 输入：aliceSizes = [1,2,5], bobSizes = [2,4]
// 输出：[5,4]

public class FairCandySwap {

    // 设alice糖果总数为 sumA，需要拿出的糖果数为 x，bob糖果总数为 sumB，需要拿出的糖果数为 y，列函数式可得 sumA-x+y = sumB-y+x，
    // 变换后可得 x = y+(sumA - sumB)/2，只需要遍历bobSizes找出符合该公式的 x ，y 即可。
    public int[] fairCandySwap(int[] aliceSizes, int[] bobSizes){
        int sumA = Arrays.stream(aliceSizes).sum();
        int sumB = Arrays.stream(bobSizes).sum();
        int delta = (sumA - sumB) / 2;
        Set<Integer> set = new HashSet<>();
        for (int num : aliceSizes){
            set.add(num);
        }
        int[] res = new int[2];
        for (int y : bobSizes){
            int x = y + delta;
            if (set.contains(x)) {
                res[0] = x;
                res[1] = y;
                break;
            }
        }
        return res;
    }
}


//Python

// class FairCandySwap:
//     def fairCandySwap(self, aliceSizes: List[int], bobSizes: List[int]) -> List[int]:
//         sumA, sumB = sum(aliceSizes), sum(bobSizes)
//         delta = (sumA - sumB) // 2
//         rec = set(aliceSizes)
//         ans = None
//         for y in bobSizes:
//             x = y + delta
//             if x in rec:
//                 ans = [x, y]
//                 break
//         return ans


//Golang

// func fairCandySwap(aliceSizes []int, bobSizes []int) []int {
//     sumA := 0
//     setA := map[int]struct{}{}
//     for _, v := range aliceSizes {
//         sumA += v
//         setA[v] = struct{}{}
//     }
//     sumB := 0
//     for _, v := range bobSizes {
//         sumB += v
//     }
//     delta := (sumA - sumB) / 2
//     for i := 0; ; i++ {
//         y := bobSizes[i]
//         x := y + delta
//         if _, has := setA[x]; has {
//             return []int{x, y}
//         }
//     }
// }

