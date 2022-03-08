package Algorithm.sort;

import java.util.Arrays;
/**
 * 比较相邻的元素，如果第一个比第二个大就交换，直到最后，做完后末尾的元素是最大的。
 */

public class BubbleSort{

    public static void bubbleSort(int[] nums){
        for(int i = 0;i<nums.length;i++){
            for(int j = 0;j<nums.length-i-1;j++){
                if(nums[j]>nums[j+1]){
                    swap(nums, j, j+1);
                }
            }
        }
    }

    public static void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    public static void main(String[] args){
        int[] nums={1,2,7,5,9,8};
        bubbleSort(nums);
        System.out.println(Arrays.toString(nums));
    }

}