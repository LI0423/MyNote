package Algorithm.leetcode;

//997. 找到小镇的法官

// 小镇里有 n 个人，按从 1 到 n 的顺序编号。传言称，这些人中有一个暗地里是小镇法官。
// 如果小镇法官真的存在，那么：
// 小镇法官不会信任任何人。
// 每个人（除了小镇法官）都信任这位小镇法官。
// 只有一个人同时满足属性 1 和属性 2 。
// 给你一个数组 trust ，其中 trust[i] = [ai, bi] 表示编号为 ai 的人信任编号为 bi 的人。
// 如果小镇法官存在并且可以确定他的身份，请返回该法官的编号；否则，返回 -1 。

// 示例 1：

// 输入：n = 2, trust = [[1,2]]
// 输出：2
// 示例 2：

// 输入：n = 3, trust = [[1,3],[2,3]]
// 输出：3
// 示例 3：

// 输入：n = 3, trust = [[1,3],[2,3],[3,1]]
// 输出：-1

public class FindJudge {

    // 图，各点的入度和出度，根据题意法官的入度应为 n-1 ，出度应为 0，先把各点的入度和出度存起来，然后遍历结果找出符合条件的数据。
    public int findJudge(int n, int[][] trust) {
        int[] inDegress = new int[n + 1];
        int[] outDegress = new int[n + 1];
        for (int[] edge : trust) {
            int x = edge[0];
            int y = edge[1];
            ++inDegress[y];
            ++outDegress[x];
        }
        for (int i = 1; i <= n; ++i){
            if (inDegress[i] == n - 1 && outDegress[i] == 0) {
                return i;
            }
        }
        return -1;
    }
    
}


// Python

// class FindJudge:
//     def findJudge(self, n: int, trust: List[List[int]]) -> int:
//         inDegress = Counter(y for _, y in trust)
//         outDegress = Counter(x for x, _ in trust)
//         return next((i for i in range (1, n + 1) if inDegress[i] == n - 1 and outDegress[i] == 0), -1)


// Golang

// func findJudge(n int, trust [][]int) int {
//     inDegress = make([]int, n + 1)
//     outDegress = make([]int, n + 1)
//     for _, t := range trust {
//         inDegress[t[1]]++
//         outDegress[t[0]]++
//     }
//     for i := 1; i <= n; ++i {
//         if inDegress[i] == n - 1 && outDegress[i] == 0 {
//             return i
//         }
//     }
//     return -1
// }
