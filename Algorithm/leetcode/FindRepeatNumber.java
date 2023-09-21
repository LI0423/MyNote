package Algorithm.leetcode;

public class FindRepeatNumber {

    public int findRepeatNumber(int[] nums){
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
    
    public void swap(int[] nums,int i,int j){
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t; 
    }
}
