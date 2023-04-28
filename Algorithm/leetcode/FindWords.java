package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

//500. 键盘行
// 给你一个字符串数组 words ，只返回可以使用在 美式键盘 同一行的字母打印出来的单词。键盘如下图所示。

// 第一行由字符 "qwertyuiop" 组成。
// 第二行由字符 "asdfghjkl" 组成。
// 第三行由字符 "zxcvbnm" 组成。

// 示例 1：

// 输入：words = ["Hello","Alaska","Dad","Peace"]
// 输出：["Alaska","Dad"]
// 示例 2：

// 输入：words = ["omk"]
// 输出：[]
// 示例 3：

// 输入：words = ["adsdf","sfd"]
// 输出：["adsdf","sfd"]

public class FindWords {
    static String[] keys = new String[]{"qwertyuiop", "asdfghjkl", "zxcvbnm"};
    static int[] hash = new int[26];
    static {
        for(int i = 0; i < keys.length; i++){
            for(char c : keys[i].toCharArray()) {
                hash[c - 'a'] = i;
            }
        }
    }

    public String[] findWords(String[] words){
        List<String> list = new ArrayList<>();
        out: for(String w : words) {
            int t = -1;
            for (char c : w.toCharArray()) {
                c = Character.toLowerCase(c);
                if (t == -1){
                    t = hash[c - 'a'];
                } else if (t != hash[c - 'a']){
                    continue out;
                }
            }
            list.add(w);
        }
        return list.toArray(new String[list.size()]);
    }

    public static void main(String[] args) {
        String[] words = new String[]{"apple", "banana"};
        new FindWords().findWords(words);
    }
}