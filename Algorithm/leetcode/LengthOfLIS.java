package Algorithm.leetcode;
/**
 * 动态规划经典     https://juejin.cn/post/6951922898638471181
 */

public class LengthOfLIS {

    public int lengthOfLIS(int[] nums){
        if(nums.length == 0){
            return 0;
        }

        int[] dp =new int[nums.length];

        //初始化就是边界情况
        dp[0] = 1;
        int maxans = 1;
        //自底向上遍历
        for(int i = 1 ;i < nums.length; i++){
            dp[i] = 1;
            //从下标0到i遍历
            for(int j = 0; j < i ; j++){
                //找到前面比nums[i]小的数nums[j]，即有dp[i]=dp[j]+1
                if(nums[j] < nums[i]){
                    //因为会有多个小于nums[i]的数，也就是会存在多种组合，取最大放到dp[i]
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
            //求出dp[i]后，dp最大那个就是nums的最长递增子序列
            maxans = Math.max(maxans, dp[i]);
        }
        return maxans;
    }
    
}
