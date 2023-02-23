package Algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

//345. 反转字符串中的元音字母

// 给你一个字符串 s ，仅反转字符串中的所有元音字母，并返回结果字符串。
// 元音字母包括 'a'、'e'、'i'、'o'、'u'，且可能以大小写两种形式出现不止一次。

// 示例 1：

// 输入：s = "hello"
// 输出："holle"
// 示例 2：

// 输入：s = "leetcode"
// 输出："leotcede"

public class ReverseVowels {
    public String reverseVowels(String s){
        int left = 0;
        int right = s.length() - 1;
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        char[] str = s.toCharArray();
        Set<Character> set = new HashSet<>(vowels.length);
        for (Character c : vowels){
            set.add(c);
            set.add(Character.toUpperCase(c));
        }
        while( left < right) {
            if(set.contains(str[left]) && set.contains(str[right])){
                char tmp = str[left];
                str[left] = str[right];
                str[right] = tmp;
                --right;
                ++left;
            }
            if(!set.contains(str[right])){
                --right;
            }
            if(!set.contains(str[left])){
                ++left;
            }
        }
        return String.valueOf(str);
    }
    public static void main(String[] args) {
        Set<Character> set = new HashSet<>();
        System.out.println(set.add('a'));
        System.out.println(set.add('a'));
    }
}

//Golang

// func reverseVowels(s string) string {
//     left, right := 0, len(s)-1
//     t := []byte{s}
//     for left < right {
//         for left < len(s) && !strings.Contains("aeiouAEIOU", string(t[left])) {
//             left++
//         }
//         for right > 0 && !strings.Contains("aeiouAEIOU", string(t[right])) {
//             right--
//         }
//         if (left < right) {
//             t[left], t[right] = t[right], t[left]
//             left++
//             right--
//         }
//     }
//     return string(t)
// }
