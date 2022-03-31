package Algorithm.sort;

import java.util.Arrays;

/**
 * 任取待排序序列的一个元素作为枢轴元素，将所有比枢轴元素小的放在左边，将所有比它大的放在右边，形成左右两个子表。
 * 再对左右两个子表按照前面的算法进行排序，直到每个子表的元素只剩一个。
 * 时间复杂度：最好O(nlogn)，最坏O(n^2)
 */

public class QuickSort {

    private static int[] quickSort(int[] nums,int left,int right){
        if(left>=right){
            return nums;
        }

        int i = left-1,j=right+1;
        int x = nums[left];
        while(i<j){
            while(nums[++i]<x);
            while(nums[--j]>x);
            if(i<j){
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        quickSort(nums, left, j);
        quickSort(nums, j+1, right);
        return nums;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{9,8,7,6,5,4,3,2,1};
        quickSort(nums, 0, nums.length-1);
        System.out.println(Arrays.toString(nums));
    }
    
}
