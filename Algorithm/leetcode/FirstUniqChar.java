package Algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

// 387. 字符串中的第一个唯一字符

// 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1 。

// 示例 1：

// 输入: s = "leetcode"
// 输出: 0
// 示例 2:

// 输入: s = "loveleetcode"
// 输出: 2
// 示例 3:

// 输入: s = "aabb"
// 输出: -1

public class FirstUniqChar{
    public int firstUniqChar(String s){
        Map<Character, Integer> frequeceMap = new HashMap<>();
        for(int i = 0; i < s.length(); i++){
            frequeceMap.put(s.charAt(i), frequeceMap.getOrDefault(s.charAt(i), 0) + 1);
        }
        for(int j = 0; j < s.length(); j++){
            if(frequeceMap.get(s.charAt(j)) == 0){
                return j;
            }
        }
        return -1;
    }
}

//Golang

// func firstUniqChar (s string) int {
//     cnt := [26]int{}
//     for _, ch := range s {
//         cnt[ch-'a']++
//     }
//     for i, ch := range s {
//         if cnt[ch-'a'] == 1 {
//             return i
//         }
//     }
//     return -1
// }