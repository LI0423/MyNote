package Algorithm.leetcode;

//409. 最长回文串

// 给定一个包含大写字母和小写字母的字符串 s ，返回 通过这些字母构造成的 最长的回文串 。
// 在构造过程中，请注意 区分大小写 。比如 "Aa" 不能当做一个回文字符串。

// 示例 1:

// 输入:s = "abccccdd"
// 输出:7
// 解释:
// 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
// 示例 2:

// 输入:s = "a"
// 输出:1
// 示例 3：

// 输入:s = "aaaaaccc"
// 输出:7

public class LongestPalindrome {
    public int longestPalindrome(String s){
        int[] count = new int[128];
        for(int i = 0; i < s.length(); i++) {
            count[s.charAt(i)]++;
        }
        int ans = 0;
        for (int c : count) {
            ans += c / 2 * 2;
            if (c % 2 == 1 && ans % 2 == 1){
                ans++;
            }
        }
        return ans;
    }
}