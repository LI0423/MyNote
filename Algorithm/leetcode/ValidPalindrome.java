package Algorithm.leetcode;

/**
 * LCR 019. 验证回文串 II

给定一个非空字符串 s，请判断如果 最多 从字符串中删除一个字符能否得到一个回文字符串。

示例 1:
输入: s = "aba"
输出: true

示例 2:
输入: s = "abca"
输出: true
解释: 可以删除 "c" 字符 或者 "b" 字符

示例 3:
输入: s = "abc"
输出: false
 */

public class ValidPalindrome {

    public static boolean validPalindrome(String s){
        for (int i = 0, j = s.length() - 1; i <= j; i++, j--){
            if (s.charAt(i) == s.charAt(j)){
                continue;
            } else {
                return checkPalindrome(i, j - 1, s) || checkPalindrome(i + 1, j, s);
            }
        }
        return true;
    }

    public static boolean checkPalindrome(int low, int high, String s){
        while (low < high){
            if (s.charAt(low) == s.charAt(high)){
                low++;
                high--;
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "abc";
        System.out.println(validPalindrome(s));
    }
}
