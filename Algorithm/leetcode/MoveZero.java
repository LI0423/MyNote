package Algorithm.leetcode;

public class MoveZero {

    private static int[] moveZero(int[] nums){
        int left = 0;
        for(int i = 0;i<nums.length;i++){
            if(nums[i] != 0){
                int t = nums[left];
                nums[left] = nums[i];
                nums[i] = t;
                left++;
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,0,3,4};
        moveZero(nums);
    }
    
}
