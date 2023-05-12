package Algorithm.leetcode;

//541. 反转字符串 II

// 给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。
// 如果剩余字符少于 k 个，则将剩余字符全部反转。
// 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。

// 示例 1：

// 输入：s = "abcdefg", k = 2
// 输出："bacdfeg"
// 示例 2：

// 输入：s = "abcd", k = 2
// 输出："bacd"

public class ReverseStr {
    public String reverseStr(String s, int k){
        if(s == null || s == ""){
            return "";
        }
        if (k == 0) {
            return s;
        }
        int index = 0;
        int length = s.length();
        StringBuilder res = new StringBuilder();

        while(index < length) {
            int end = Math.min(index + k, length);
            StringBuilder sb = new StringBuilder(s.substring(index, end));
            if((index / k) % 2 == 0) {
                res.append(sb.reverse());
            } else {
                res.append(sb);
            }
            index = end;
        }

        return res.toString();
    }

    public String reverseStr2(String s, int k){
        char[] charArray = s.toCharArray();
        int n = s.length();
        for (int l = 0; l < n; l = l + 2 * k){
            int r = l + k - 1;
            reverse(charArray, l, Math.min(r, n-1));
        }
        return String.valueOf(charArray);
    }

    public void reverse(char[] chars, int l, int r) {
        while(l < r) {
            char c = chars[l];
            chars[l] = chars[r];
            chars[r] = c;
            l++;
            r--;
        }
    }

    public static void main(String[] args) {
        String s = "abcdefg";
        ReverseStr reverseStr = new ReverseStr();
        reverseStr.reverseStr2(s, 2);
    }


}
