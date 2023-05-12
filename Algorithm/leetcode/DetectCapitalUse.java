package Algorithm.leetcode;

//520. 检测大写字母

// 我们定义，在以下情况时，单词的大写用法是正确的：
// 全部字母都是大写，比如 "USA" 。
// 单词中所有字母都不是大写，比如 "leetcode" 。
// 如果单词不只含有一个字母，只有首字母大写， 比如 "Google" 。
// 给你一个字符串 word 。如果大写用法正确，返回 true ；否则，返回 false 。

// 示例 1：

// 输入：word = "USA"
// 输出：true
// 示例 2：

// 输入：word = "FlaG"
// 输出：false

public class DetectCapitalUse {
    public boolean detectCapitalUse(String word) {
        if (word == null || "".equals(word)) {
            return false;
        }
        char[] chars = word.toCharArray();
        if((int)chars[0] >= 97 && (int)chars[0] <= 122) {
            for(char c : chars) {
                if((int) c < 97) {
                    return false;
                }
            }
        } else if ((int)chars[0] >= 65 && (int)chars[0] <=90 && (int) chars[1] >= 65 && (int) chars[1]<= 90){
            for(int i = 2; i < chars.length; i++) {
                if((int) chars[i] > 90) {
                    return false;
                }
            }
        } else if((int)chars[0] >= 65 && (int) chars[0] <= 90 && (int) chars[1] >= 97 && (int) chars[1]<= 122) {
            for(int i = 2; i < chars.length; i++) {
                if((int) chars[i] <= 90) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean detectCapitalUse2(String word) {
        if (word.toUpperCase().equals(word)) return true;
        if (word.toLowerCase().equals(word)) return true;
        int n = word.length(), idx = 1;
        if (Character.isUpperCase(word.charAt(0))) {
            while (idx < n && Character.isLowerCase(word.charAt(idx))) idx++;
        }
        return idx == n;
    }

    public static void main(String[] args) {
        String word = "USA";
        new DetectCapitalUse().detectCapitalUse(word);
    }
}
