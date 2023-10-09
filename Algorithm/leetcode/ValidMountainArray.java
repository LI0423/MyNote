package Algorithm.leetcode;

//941. 有效的山脉数组

// 给定一个整数数组 arr，如果它是有效的山脉数组就返回 true，否则返回 false。
// 让我们回顾一下，如果 arr 满足下述条件，那么它是一个山脉数组：
// arr.length >= 3
// 在 0 < i < arr.length - 1 条件下，存在 i 使得：
// arr[0] < arr[1] < ... arr[i-1] < arr[i] 
// arr[i] > arr[i+1] > ... > arr[arr.length - 1]

// 示例 1：

// 输入：arr = [2,1]
// 输出：false
// 示例 2：

// 输入：arr = [3,5,5]
// 输出：false
// 示例 3：

// 输入：arr = [0,3,2,1]
// 输出：true

public class ValidMountainArray {

    // 单指针两次遍历，第一次遍历找到递增到最大数的下标，第二次从最大数的下标开始遍历，找到最后；
    // 如果遍历结束，指针的值与末尾数字的下标一致，则认为该数组符合条件。
    public boolean validMountainArray(int[] arr) {
        if (arr.length < 3) {
            return false;
        }
        int i = 0;
        int n = arr.length;
        while (i + 1 < n && arr[i] < arr[i+1]) {
            i++;
        }
        if (i == 0 || i == n - 1) {
            return false;
        }
        while (i + 1 < n && arr[i] > arr[i+1]) {
            i++;
        }
        return i == arr.length - 1;
    }
}
