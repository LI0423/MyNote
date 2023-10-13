package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//1002. 查找共用字符

// 给你一个字符串数组 words ，请你找出所有在 words 的每个字符串中都出现的共用字符（ 包括重复字符），并以数组形式返回。你可以按 任意顺序 返回答案。

// 示例 1：

// 输入：words = ["bella","label","roller"]
// 输出：["e","l","l"]
// 示例 2：

// 输入：words = ["cool","lock","cook"]
// 输出：["c","o"]

public class CommonChars {

    // 第一次遍历记录每个字符串中每个字符出现的频率，然后记录各个字符出现的最小次数。
    public List<String> commonChars(String[] words) {

        int[] minFreq = new int[26];
        Arrays.fill(minFreq, Integer.MAX_VALUE);
        for (String word : words) {
            int[] freq = new int[26];
            int length = word.length();
            for (int i = 0; i < length; i++){
                char c = word.charAt(i);
                ++freq[c - 'a'];
            }
            for (int i = 0; i < 26; i++){
                minFreq[i] = Math.min(minFreq[i], freq[i]);
            }
        }

        List<String> resList = new ArrayList<String>();
        for (int i = 0; i < 26; i++){
            for (int j = 0; j < minFreq[i]; ++j) {
                System.out.print(String.valueOf((char) (i + 'a')));
                resList.add(String.valueOf((char) (i + 'a')));
            }
        }
        return resList;
    }

    public static void main(String[] args) {
        String[] words = {"roller", "bella", "label"};
        new CommonChars().commonChars(words);
    }
    
}


// Python

// class CommonChars:
//     def commonChars(self, words: List[str]) -> List[str]:
//         minFreq = [float("inf")] * 26
//         for word in words:
//             freq = [0] * 26
//             for ch in word:
//                 freq[ord(ch) - ord("a")] += 1
//             for i in range(26):
//                 minFreq[i] = min(minFreq[i], freq[i])

//         ans = list()
//         for i in range(26):
//             ans.extend([chr(i + ord("a"))]) * minFreq[i]
//         return ans


// Golang

// func commonChars(words []string) (ans []string) {
//     minFreq := [26]int{}
//     for i in range minFreq{
//         minFreq[i] = math.MaxInt64
//     }
//     for _, word := range words {
//         freq := [26]int{}
//         for _, b := range word {
//             freq[b - 'a']++
//         }
//         for i, f := range freq[:] {
//             if f < minFreq[i] {
//                 minFreq[i] = f
//             }
//         }
//     }
//     for i := byte(0); i < 26; i++ {
//         for j := 0; j < minFreq[i]; j++ {
//             ans = append(ans, string('a' + i))
//         }
//     }
//     return
// }
