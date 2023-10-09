package Algorithm.leetcode;

//942. 增减字符串匹配

// 由范围 [0,n] 内所有整数组成的 n + 1 个整数的排列序列可以表示为长度为 n 的字符串 s ，其中:
// 如果 perm[i] < perm[i + 1] ，那么 s[i] == 'I' 
// 如果 perm[i] > perm[i + 1] ，那么 s[i] == 'D' 
// 给定一个字符串 s ，重构排列 perm 并返回它。如果有多个有效排列perm，则返回其中 任何一个 。

// 示例 1：

// 输入：s = "IDID"
// 输出：[0,4,1,3,2]
// 示例 2：

// 输入：s = "III"
// 输出：[0,1,2,3]
// 示例 3：

// 输入：s = "DDI"
// 输出：[3,2,0,1]

public class DiStringMatch {

    // 由题可知，当字符串从I开始时，数组从0开始，当字符串从D开始时，数组从字符串的长度开始，
    // 下一个字符如果是I则递增，如果是D则递减，而到最后一个字符，递增和递减的数字大小是一样，所以选一个给数组赋值即可。
    public int[] diStringMatch(String s) {
        int n = s.length();
        int lo = 0, hi = n;
        int[] res = new int[n + 1];
        
        for (int i = 0; i < n; i++){
            if (s.charAt(i) == 'I') {
                res[i] = lo++;
            } else {
                res[i] = hi--;
            }
        }
        res[n] = lo;
        return res;
    }
    
}
