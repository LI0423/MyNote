package Algorithm.leetcode;

// 67. 二进制求和

// 给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。 

// 示例 1：

// 输入:a = "11", b = "1"
// 输出："100"
// 示例 2：

// 输入：a = "1010", b = "1011"
// 输出："10101"

//具体的，我们可以取 n=max⁡{∣a∣,∣b∣}，循环 n 次，从最低位开始遍历。我们使用一个变量 carry 表示上一个位置的进位，初始值为 0。记当前位置对其的两个位为 ai 
//和 bi，则每一位的答案为 (carry+ai+bi) mod 2，下一位的进位为 ⌊（carry+ai+bi）/2⌋。重复上述步骤，直到数字 a 和 b 的每一位计算完毕。最后如果 carry 的最高位不为 0，则将最高位添加到计算结果的末尾。


public class AddBinary {
    public String addBinary(String a, String b) {
        StringBuffer strb = new StringBuffer();
        int n = Math.max(a.length(), b.length()), carry = 0;
        for (int i = 0; i < n; ++i){
            carry += i < a.length() ? (a.charAt(a.length() - 1 - i) - '0') : 0;
            carry += i < b.length() ? (b.charAt(b.length() - 1 - i) - '0') : 0;
            strb.append((char) (carry % 2 + '0'));
            carry /= 2; 
        }
        if (carry > 0){
            strb.append('1');
        }
        strb.reverse();
        return strb.toString();
    }
}
