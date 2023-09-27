package Algorithm.leetcode;

import java.util.Arrays;

//917. 仅仅反转字母

// 给你一个字符串 s ，根据下述规则反转字符串：
// 所有非英文字母保留在原有位置。
// 所有英文字母（小写或大写）位置反转。
// 返回反转后的 s 。

// 示例 1：

// 输入：s = "ab-cd"
// 输出："dc-ba"
// 示例 2：

// 输入：s = "a-bC-dEf-ghIj"
// 输出："j-Ih-gfE-dCba"
// 示例 3：

// 输入：s = "Test1ng-Leet=code-Q!"
// 输出："Qedo1ct-eeLg=ntse-T!"

public class ReverseOnlyLetters {

    public String reverseOnlyLetters(String s){
        int n = s.length();
        StringBuffer sb = new StringBuffer();
        for (int i = n - 1; i >= 0; i--){
            if (Character.isLetter(s.charAt(i))){
                sb.append(s.charAt(i));
            }
        }
        for (int j = 0; j < n; j++){
            if (!Character.isLetter(s.charAt(j))) {
                sb.insert(j, s.charAt(j));
            }
        }
        return sb.toString();
    }

    public String reverseOnlyLetters2(String s){
        int n = s.length();
        int left = 0, right = n - 1;
        char[] charArray = s.toCharArray();
        while(true) {
            while(left < right && !Character.isLetter(charArray[left])){
                left++;
            }
            while(right > left && !Character.isLetter(charArray[right])){
                right--;
            }
            if (left >= right) {
                break;
            }
            char tmp = charArray[left];
            charArray[left] = charArray[right];
            charArray[right] = tmp;
            left++;
            right--;
        }
        return new String(charArray);
    }
    
}
