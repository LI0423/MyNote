package Algorithm.leetcode.greedy;

/**
 * 2224. 转化时间需要的最少操作数

给你两个字符串 current 和 correct ，表示两个 24 小时制时间 。
24 小时制时间 按 "HH:MM" 进行格式化，其中 HH 在 00 和 23 之间，而 MM 在 00 和 59 之间。最早的 24 小时制时间为 00:00 ，最晚的是 23:59 。
在一步操作中，你可以将 current 这个时间增加 1、5、15 或 60 分钟。你可以执行这一操作 任意 次数。
返回将 current 转化为 correct 需要的 最少操作数 。

示例 1：
输入：current = "02:30", correct = "04:35"
输出：3
解释：
可以按下述 3 步操作将 current 转换为 correct ：
- 为 current 加 60 分钟，current 变为 "03:30" 。
- 为 current 加 60 分钟，current 变为 "04:30" 。 
- 为 current 加 5 分钟，current 变为 "04:35" 。
可以证明，无法用少于 3 步操作将 current 转化为 correct 。

示例 2：
输入：current = "11:00", correct = "11:01"
输出：1
解释：只需要为 current 加一分钟，所以最小操作数是 1 。
 */

public class ConvertTime {

    public static int convertTime(String current, String correct){
        String[] currents = current.split(":");
        String[] corrects = correct.split(":");
        int total = (Integer.parseInt(corrects[0]) - Integer.parseInt(currents[0])) * 60 + (Integer.parseInt(corrects[1]) - Integer.parseInt(currents[1]));
        int count = 0;
        int[] mins = {60, 15, 5, 1}; 
        for (int i : mins){
            count += total / i;
            total %= i;
        }

        return count;
    }

    public static void main(String[] args) {
        String current = "00:00";
        String correct = "23:59";
        System.out.println(convertTime(current, correct)); 
    }
}
