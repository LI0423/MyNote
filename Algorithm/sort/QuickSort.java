package Algorithm.sort;

import java.util.Arrays;

import javax.management.Query;

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
