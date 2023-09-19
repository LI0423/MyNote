package Algorithm.leetcode;

//796. 旋转字符串

// 给定两个字符串, s 和 goal。如果在若干次旋转操作之后，s 能变成 goal ，那么返回 true 。
// s 的 旋转操作 就是将 s 最左边的字符移动到最右边。 
// 例如, 若 s = 'abcde'，在旋转一次之后结果就是'bcdea' 。

// 示例 1:

// 输入: s = "abcde", goal = "cdeab"
// 输出: true
// 示例 2:

// 输入: s = "abcde", goal = "abced"
// 输出: false

public class RotateString {

    public Boolean rotateString(String s, String goal){
        int m = s.length(), n = goal.length();
        if(s == null || m != n){
            return false;
        }
        for (int i = 0; i < n; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (s.charAt((i + j) % n) != goal.charAt(j)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return true;
            }
        }
        return false;
    }

    public Boolean rotateString2(String s, String goal){
        return s.length() == goal.length() && (s + s).contains(goal);
    }
    
}
