package Algorithm.leetcode;

// 504. 七进制数

// 给定一个整数 num，将其转化为 7 进制，并以字符串形式输出。

// 示例 1:

// 输入: num = 100
// 输出: "202"
// 示例 2:

// 输入: num = -7
// 输出: "-10"

public class ConvertToBase7 {
    public String convertToBase7(int num){
        if (num == 0) return "0";
        boolean flag = num < 0;
        StringBuilder sb = new StringBuilder();
        while(num != 0) {
            sb.append(num % 7);
            num /= 7;
        }
        sb.reverse();
        return flag ? "-" + sb.toString() : sb.toString();
    }
}
