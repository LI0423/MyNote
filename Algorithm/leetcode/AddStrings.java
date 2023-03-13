package Algorithm.leetcode;

//415. 字符串相加

// 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和并同样以字符串形式返回。
// 你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式。

// 示例 1：

// 输入：num1 = "11", num2 = "123"
// 输出："134"
// 示例 2：

// 输入：num1 = "456", num2 = "77"
// 输出："533"
// 示例 3：

// 输入：num1 = "0", num2 = "0"
// 输出："0"

public class AddStrings {
    //我们定义两个指针 iii 和 jjj 分别指向 num1 和 num2 的末尾，即最低位，同时定义一个变量 add 维护当前是否有进位，
    //然后从末尾到开头逐位相加即可。你可能会想两个数字位数不同怎么处理，这里我们统一在指针当前下标处于负数的时候返回 0，
    //等价于对位数较短的数字进行了补零操作，这样就可以除去两个数字位数不同情况的处理。
    public String addStrings(String s1, String s2){
        int i = s1.length()-1, j = s2.length()-1, add = 0;
        StringBuffer ans = new StringBuffer();
        while (i >= 0 || j >= 0 || add != 0){
            int x = i >= 0 ? s1.charAt(i) - '0' : 0;
            int y = j >= 0 ? s2.charAt(j) - '0' : 0;
            int result = x + y + add;
            ans.append(result % 10);
            add = result / 10;
            i--;
            j--;
        }
        ans.reverse();
        return ans.toString();
    }

    public static void main(String[] args) {
        String s1 = "12";
        String s2 = "23";
        new AddStrings().addStrings(s1, s2);
    }
}


//Golang

// func addStrings(s1 string, s2 string) string {
//     add := 0
//     ans := ""
//     for i, j := len(s1)-1, len(s2)-1; i >= 0 || j >= 0 || add != 0 {
//         var x, y int
//         if i >= 0 {
//             x = int(s1[i] - '0')
//         }
//         if j >= 0 {
//             y = int(s2[j] - '0')
//         }
//         result := x + y + add
//         ans = strconv.Itoa(result % 10) + ans
//         add = result / 10
//     }
//     return result
// }
