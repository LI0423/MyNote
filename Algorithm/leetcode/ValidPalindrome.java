package Algorithm.leetcode;

//680. 验证回文串 II

// 给你一个字符串 s，最多 可以从中删除一个字符。
// 请你判断 s 是否能成为回文字符串：如果能，返回 true ；否则，返回 false 。

// 示例 1：

// 输入：s = "aba"
// 输出：true
// 示例 2：

// 输入：s = "abca"
// 输出：true
// 解释：你可以删除字符 'c' 。
// 示例 3：

// 输入：s = "abc"
// 输出：false

public class ValidPalindrome {

    public boolean validPalindrome(String s){
        char[] charArray = s.toCharArray();
        for(int i = 0, j = charArray.length - 1; i <= j;){
            if(charArray[i] == charArray[j]){
                i++;
                j--;
            } else {
                return validPalindrome(s, i, j - 1) || validPalindrome(s, i+1, j);
            }
        }
        return true;
    }

    public boolean validPalindrome(String s, int low, int high){
        for(int i = low, j = high; i < j; ++i, --j){
            char c1 = s.charAt(i), c2 = s.charAt(j);
            if(c1 != c2){
                return false;
            }
        }
        return true;
    }
    
}
