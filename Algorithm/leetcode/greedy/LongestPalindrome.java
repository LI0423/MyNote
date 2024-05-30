package Algorithm.leetcode.greedy;

/**
 * 409. 最长回文串

给定一个包含大写字母和小写字母的字符串 s ，返回 通过这些字母构造成的 最长的 
回文串。
在构造过程中，请注意 区分大小写 。比如 "Aa" 不能当做一个回文字符串。

示例 1:
输入:s = "abccccdd"
输出:7
解释:
我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。

示例 2:
输入:s = "a"
输出:1
解释：可以构造的最长回文串是"a"，它的长度是 1。
 */

public class LongestPalindrome {

    public static int longestPalindrome(String s){
        int n = s.length();
        int[] count = new int[128];
        for (int i = 0; i < n; i++){
            char c = s.charAt(i);
            count[c] += 1; 
        }
        int ans = 0;
        for (int v : count){
            ans += v / 2 * 2;
            if (v % 2 == 1 && ans % 2 == 0){
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "abccccdd";
        int longestPalindrome = longestPalindrome(s);
        System.out.println(longestPalindrome);
    }
}
