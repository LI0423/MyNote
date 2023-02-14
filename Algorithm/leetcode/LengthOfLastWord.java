package Algorithm.leetcode;
//58. 最后一个单词的长度
// 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。

// 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。

// 示例 1：

// 输入：s = "Hello World"
// 输出：5
// 解释：最后一个单词是“World”，长度为5。
// 示例 2：

// 输入：s = "   fly me   to   the moon  "
// 输出：4
// 解释：最后一个单词是“moon”，长度为4。
// 示例 3：

// 输入：s = "luffy is still joyboy"
// 输出：6
// 解释：最后一个单词是长度为6的“joyboy”。
public class LengthOfLastWord {
    public int lengthOfLastWord(String str){
        // String [] strs = str.split(" ");
        // return strs[strs.length-1].length();

        int index = str.length() - 1;
        while(str.charAt(index) == ' '){
            index --;
        }

        int len = 0;
        while(index >= 0 && str.charAt(index) != ' '){
            len ++;
            index --;
        }
        return len;
    }
}

//Golang

// func lengthOfLastWord(str String){
//     index := len(str) - 1
//     for str[index] == ' ' {
//         index --
//     }
//     len := 0
//     for index >= 0 && str[index] != ' '{
//         len ++
//         index --
//     }
//     return len
// }
