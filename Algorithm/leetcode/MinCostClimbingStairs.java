package Algorithm.leetcode;

public class MinCostClimbingStairs {

    private static int minCostClimbingStairs(int[] nums){
        int a = 0, b = 0;
        for(int i = 1; i<nums.length; ++i){
            int c = Math.min(a+nums[i-1], b+nums[i]);
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,100,1,1,1,100,1,1,100,1};
        minCostClimbingStairs(nums);
    }
    
}
