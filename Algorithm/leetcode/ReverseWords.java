package Algorithm.leetcode;

//557. 反转字符串中的单词 III

// 给定一个字符串 s ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。

// 示例 1：

// 输入：s = "Let's take LeetCode contest"
// 输出："s'teL ekat edoCteeL tsetnoc"
// 示例 2:

// 输入： s = "God Ding"
// 输出："doG gniD"

public class ReverseWords {
    public String reverseWords(String s){
        String res = "";
        StringBuilder tmp = new StringBuilder();
        for(String word : s.split(" ")){
            res += " " + tmp.append(word).reverse().toString();
            tmp.delete(0, 1);
        }
        return res.replaceFirst(" ", "");
    }

    public static void main(String[] args) {
        String s = "Let's take LeetCode contest";
        new ReverseWords().reverseWords(s);
    }
}
