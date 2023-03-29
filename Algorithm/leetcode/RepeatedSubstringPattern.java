package Algorithm.leetcode;

import java.util.Arrays;

public class RepeatedSubstringPattern{
    public boolean repeatedSubstringPattern(String s){
        return kmp(s + s, s);
    }
    public boolean kmp(String query, String pattern){
        int n = query.length();
        int m = pattern.length();

        int[] fail = new int[m];
        Arrays.fill(fail, -1);
        for(int i = 1; i < m; i++) {
            int j = fail[i - 1];
            while(j != -1 && pattern.charAt(j+1) != pattern.charAt(i)){
                j = fail[j];
            }
            if(pattern.charAt(j) == pattern.charAt(i)){
                fail[i] = j + 1;
            }
        }
        int match = -1;
        for(int i = 1; i < n-1; ++i){
            while (match != -1 && pattern.charAt(match + 1) != query.charAt(i)){
                match = fail[match];
            }
            if (pattern.charAt(match + 1) == query.charAt(i)){
                ++match;
                if (match == m-1){
                    return true;
                }
            }
        }
        return false;
    }

    // 这是一个KMP字符串匹配算法的实现，用于查找一个字符串（query）中是否包含另一个字符串（pattern）。
    // 首先，算法初始化了一个fail数组，用于存储匹配失败时跳转到的位置。然后使用while循环来计算fail数组中的每个元素。
    // 在这个循环中，它比较pattern字符串中的字符，并根据当前位置和之前位置的fail值来更新fail数组。这个循环结束后，
    // 算法使用另一个while循环来遍历query字符串中的每个字符，并使用fail数组来匹配pattern字符串。如果匹配成功，则将匹配位置match增加，
    // 并判断是否已经匹配到了pattern字符串的末尾。如果是，则返回true，否则继续循环。如果所有字符都遍历完了，但没有找到匹配，则返回false。
    // 总的来说，这个算法使用了KMP算法的一些核心思想，通过预处理和利用之前匹配失败的信息来提高字符串匹配的效率。

    public boolean kmp(String pattern) {
        int n = pattern.length();
        int[] fail = new int[n];
        Arrays.fill(fail, -1);
        for (int i = 1; i < n; ++i) {
            int j = fail[i - 1];
            while (j != -1 && pattern.charAt(j + 1) != pattern.charAt(i)) {
                j = fail[j];
            }
            if (pattern.charAt(j + 1) == pattern.charAt(i)) {
                fail[i] = j + 1;
            }
        }
        return fail[n - 1] != -1 && n % (n - fail[n - 1] - 1) == 0;
    }
}

//Golang

// func repeatedSubstringPattern(s string) bool {
//     return kmp(s + s, s)
// }

// func kmp(query, pattern string) bool {
//     n, m := len(query), len(pattern)
//     fail := make([]int, m)
//     for i := 0; i < m; i++ {
//         fail[i] = -1
//     }
//     for i := 1; i < m; i++ {
//         j := fail[i - 1]
//         for j != -1 && pattern[j + 1] != pattern[i] {
//             j = fail[j]
//         }
//         if pattern[j + 1] == pattern[i] {
//             fail[i] = j + 1
//         }
//     }
//     match := -1
//     for i := 1; i < n - 1; i++ {
//         for match != -1 && pattern[match + 1] != query[i] {
//             match = fail[match]
//         }
//         if pattern[match + 1] == query[i] {
//             match++
//             if match == m - 1 {
//                 return true
//             }
//         }
//     }
//     return false
// }