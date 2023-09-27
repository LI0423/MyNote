package Algorithm.leetcode;

//844. 比较含退格的字符串

// 给定 s 和 t 两个字符串，当它们分别被输入到空白的文本编辑器后，如果两者相等，返回 true 。# 代表退格字符。
// 注意：如果对空文本输入退格字符，文本继续为空。

// 示例 1：

// 输入：s = "ab#c", t = "ad#c"
// 输出：true
// 解释：s 和 t 都会变成 "ac"。
// 示例 2：

// 输入：s = "ab##", t = "c#d#"
// 输出：true
// 解释：s 和 t 都会变成 ""。
// 示例 3：

// 输入：s = "a#c", t = "b"
// 输出：false
// 解释：s 会变成 "c"，但 t 仍然是 "b"。

public class BackspaceCompare {
    // 栈，对字符串进行从前到后的字符遍历，碰到字母时进行压栈，碰到#进行弹栈，遍历结束后栈内留下的字符就是最终结果。
    public boolean backspaceCompare(String s, String t){
        return handle(s).equals(handle(t));
    }
    public String handle(String str){
        StringBuffer sb = new StringBuffer();
        int n = str.length();
        for(int i = 0; i < n; i++){
            char c = str.charAt(i);
            if (c != '#'){
                sb.append(c);
            } else {
                if (sb.length() > 0){
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
        return sb.toString();
    }
}


//Python

// class BackspaceCompare:
//     def backspaceCompare(self, s : str, t : str) -> bool:
//         def build(s : str) -> str:
//             ret = list()
//             for c in s:
//                 if c != '#':
//                     ret.append(c)
//                 else:
//                     if len(ret) > 0:
//                         ret.pop()
//             return "".join(ret)
//         return build(s) == build(t)


//Golang

// func backspaceCompare(s, t string) bool {
//     return build(s) == build(t)
// }

// func build(str string) string {
//     s := []{}
//     for i := range str {
//         if str[i] != '#' {
//             s = append(s, str[i])
//         } else if len(s) > 0 {
//             s = s[:len(s) - 1]
//         }
//     }
//     return string(s)
// }