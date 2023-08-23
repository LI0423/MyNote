package Algorithm.leetcode;

// 709. 转换成小写字母

// 给你一个字符串 s ，将该字符串中的大写字母转换成相同的小写字母，返回新的字符串。

// 示例 1：

// 输入：s = "Hello"
// 输出："hello"
// 示例 2：

// 输入：s = "here"
// 输出："here"
// 示例 3：

// 输入：s = "LOVELY"
// 输出："lovely"

public class ToLowerCase {

    public String toLowerCase(String str){
        return str.toLowerCase();
    }
    
    public String toLowerCase2(String s){
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()){
            if(c >= 65 && c <= 90){
                c |= 32;
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
