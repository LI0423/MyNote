package Algorithm.leetcode.greedy;

/**
 * 2259. 移除指定数字得到的最大结果
简单

相关标签
相关企业

提示
给你一个表示某个正整数的字符串 number 和一个字符 digit 。

从 number 中 恰好 移除 一个 等于 digit 的字符后，找出并返回按 十进制 表示 最大 的结果字符串。生成的测试用例满足 digit 在 number 中出现至少一次。

 

示例 1：

输入：number = "123", digit = "3"
输出："12"
解释："123" 中只有一个 '3' ，在移除 '3' 之后，结果为 "12" 。
示例 2：

输入：number = "1231", digit = "1"
输出："231"
解释：可以移除第一个 '1' 得到 "231" 或者移除第二个 '1' 得到 "123" 。
由于 231 > 123 ，返回 "231" 。

示例 3：
输入：number = "551", digit = "5"
输出："51"
解释：可以从 "551" 中移除第一个或者第二个 '5' 。
两种方案的结果都是 "51" 。
 */

public class RemoveDigit {

    public static String removeDigit(String number, char digit){
        StringBuilder sb = new StringBuilder(number);
        int max = -1;
        for (int i = 0; i < number.length(); i++){
            if (sb.charAt(i) == digit){
                max = i;
                if (i == number.length() - 1 || sb.charAt(i + 1) > digit){
                    break;
                }
            }
        }
        sb.deleteCharAt(max);
        return sb.toString();
    }

    public static void main(String[] args) {
        String number = "1231";
        char digit = '1';
        System.out.println(removeDigit(number, digit));
    }
}
