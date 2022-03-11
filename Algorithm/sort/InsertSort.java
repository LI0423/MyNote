package Algorithm.sort;

import java.util.Arrays;

public class InsertSort {

    public static int[] insertSort(int[] nums){
        for(int i = 1,j;i<nums.length;i++){
            int num = nums[i];
            for(j=i-1;j>=0 && nums[j]>num;--j){
                nums[j+1] = nums[j];
            }
            nums[j+1] = num;
        }
        return nums;
    }

    public static void main(String[] args){
        int[] nums = {1,2,7,5,9,8};
        insertSort(nums);
        System.out.println(Arrays.toString(nums));
    }
    
}
