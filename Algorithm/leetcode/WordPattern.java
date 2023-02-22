package Algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

//290. 单词规律
// 给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。

// 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双向连接的对应规律。

// 示例1:

// 输入: pattern = "abba", s = "dog cat cat dog"
// 输出: true
// 示例 2:

// 输入:pattern = "abba", s = "dog cat cat fish"
// 输出: false
// 示例 3:

// 输入: pattern = "aaaa", s = "dog cat cat dog"
// 输出: false

public class WordPattern {
    public Boolean wordPattern(String pattern, String s){
        String[] words = s.split(" ");
        Map<Object, Integer> map = new HashMap<>();
        if (words.length != pattern.length()){
            return false;
        }
        for (Integer i = 0; i < words.length; i++){
            if (map.put(pattern.charAt(i), i) != map.put(words[i], i)){
                return false;
            }
        }
        return true;
    }
}

//Golang

// func wordPattern(pattern string, s string) bool {
//     word2ch := map[string]byte{}
//     ch2word := map[byte]string{}
//     words := strings.Split(s, " ")
//     if len(pattern) != len(words) {
//         return false
//     }
//     for i, word := range words {
//         ch := pattern[i]
//         if word2ch[word] > 0 && word2ch[word] != ch || ch2word[ch] != "" && ch2word[ch] != word {
//             return false
//         }
//         word2ch[word] = ch
//         ch2word[ch] = word
//     }
//     return true
// }


