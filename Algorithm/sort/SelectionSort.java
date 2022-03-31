package Algorithm.sort;

import java.util.Arrays;
/**
 * 首先在未排序序列中找到最小的元素，存放到排序序列的起始位置；
 * 再从剩余未排序的序列中找到最小的元素存放到已排序序列的末尾；
 * 时间复杂度：O(n^2)
 */

public class SelectionSort {

    private static int[] selectionSort(int[] nums){
        for(int i =0;i<nums.length-1;i++){
            int minIndex = i;
            for(int j=i;j<nums.length;j++){
                if(nums[j]<nums[minIndex]){
                    minIndex = j;
                }
            }
            swap(nums, minIndex, i);
        }
        return nums;
    }

    private static void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp; 
    }

    public static void main(String[] args){
        int[] nums = {1,2,5,7,9,8};
        selectionSort(nums);
        System.out.println(Arrays.toString(nums));
    }
    
}
