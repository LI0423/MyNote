package Algorithm.leetcode;

public class RobII {

    public int robII(int[] nums){
        int n = nums.length;
        if(n == 1){
            return nums[0];
        }
        int s1 = robRange(nums, 0, n-2);
        int s2 = robRange(nums, 1, n-1);
        return Math.max(s1, s2);
    }

    public int robRange(int[] nums,int l , int r){
        int a = 0 , b = nums[l];
        for (int i = l + 1; i <= r; ++i) {
            int c = Math.max(a+nums[i], b);
            a = b;
            b = c;
        }
        return b;
    }
    
}
