package Algorithm.leetcode.greedy;

import java.util.Arrays;

/**
 * 3074. 重新分装苹果

给你一个长度为 n 的数组 apple 和另一个长度为 m 的数组 capacity 。
一共有 n 个包裹，其中第 i 个包裹中装着 apple[i] 个苹果。同时，还有 m 个箱子，第 i 个箱子的容量为 capacity[i] 个苹果。
请你选择一些箱子来将这 n 个包裹中的苹果重新分装到箱子中，返回你需要选择的箱子的 最小 数量。
注意，同一个包裹中的苹果可以分装到不同的箱子中。

示例 1：
输入：apple = [1,3,2], capacity = [4,3,1,5,2]
输出：2
解释：使用容量为 4 和 5 的箱子。
总容量大于或等于苹果的总数，所以可以完成重新分装。

示例 2：
输入：apple = [5,5,5], capacity = [2,4,2,7]
输出：4
解释：需要使用所有箱子。
 */

public class MinimumBoxes {

    public static int minimumBoxes(int[] apple, int[] capacity){
        int n = apple.length, m = capacity.length;
        Arrays.sort(apple);
        Arrays.sort(capacity);
        int count = 1;
        for(int i = n - 1, j = m - 1; i >= 0;){
            if (capacity[j] >= apple[i]){
                capacity[j] -= apple[i];
                i--;
            } else {
                apple[i] -= capacity[j];
                capacity[j] = 0;
                j--;
                count++;
            }
        }
        return count;
    }

    public int minimumBoxes2(int[] apple, int[] capacity) {
        int s = 0;
        for (int x : apple) {
            s += x;
        }
        Arrays.sort(capacity);
        int m = capacity.length;
        int i = m - 1;
        for (; s > 0; i--) {
            s -= capacity[i];
        }
        return m - 1 - i;
    }

    public static void main(String[] args) {
        int[] apple = {5,5,5};
        int[] capacity = {2,4,2,7};
        System.out.println(minimumBoxes(apple, capacity));
    }
}
