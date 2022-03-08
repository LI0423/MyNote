package Algorithm.sort;

import java.util.Arrays;
/**
 * 将第一待排序序列第一个元素看作一个有序序列，把第二个元素到最后一个元素的最后一个元素当成未排序序列；
 * 从头到位依次扫描
 */

public class InsertSort {

    public static void insertSort(int[] nums){
        for(int i =1,j;i<nums.length;i++){
            int num = nums[i];
            for(j = i-1;j>=0 && nums[j]>num;j--){
                nums[j+1]=nums[j];
            }
            nums[j+1] = num;
        }
    }

    public static void main(String[] args){
        int[] nums = {1,2,7,5,9,8};
        insertSort(nums);
        System.out.println(Arrays.toString(nums));
    }
    
}
