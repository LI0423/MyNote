package Algorithm.leetcode;

public class FindRepeatNumber {

    private int findRepeatNumber(int[] nums){
        for(int i = 0;i<nums.length;i++){
            while(nums[i] != i){
                if(nums[i] == nums[nums[i]]){
                    return nums[i];
                }
                swap(nums,i,nums[i]);
            }
        }
        return -1;
    }
    
    private void swap(int[] nums,int i,int j){
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t; 
    }
}
