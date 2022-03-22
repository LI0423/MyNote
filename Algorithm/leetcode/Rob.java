package Algorithm.leetcode;

public class Rob {

    public static int rob(int[] nums){
        int a = 0 , b = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            int c = Math.max(a+nums[i],b);
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,7,9,3,1};
        rob(nums);
    }
    
}
