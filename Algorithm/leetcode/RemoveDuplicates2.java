package Algorithm.leetcode;

import java.util.Arrays;

public class RemoveDuplicates2 {

    public static int removeDuplicates2(int[] nums){
        int i = 0;
        for(int num : nums){
            if(i<2||num!=nums[i-2]){
                nums[i++] = num;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,2,2,3};
        removeDuplicates2(nums);
        System.out.println(Arrays.toString(nums));
    }
    
}
