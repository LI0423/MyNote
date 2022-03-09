package Algorithm.sort;

import java.util.Arrays;

public class ShellSort {

    private static void shellSort(int[] nums){
        for (int gap = nums.length/2; gap>0 ; gap/=2) {
            for (int i = gap; i < nums.length; i++) {
                int key = nums[i];
                int j = i;
                while(j>gap && nums[j-gap]>key){
                    nums[j] =nums[j-gap];
                    j-=gap;
                }
                nums[j]=key;
            }
        }

    }

    public static void main(String[] args){
        int[] nums = {1,2,5,7,9,8};
        shellSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
