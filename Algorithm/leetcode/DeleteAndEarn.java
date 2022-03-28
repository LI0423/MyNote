package Algorithm.leetcode;

/**
 * 核心思路：一个数字要么不选，要么全选。
 * 首先计算出每个数字的总和sums，并维护两个dp数组：select 和 nonSelect
 * sums[i] 代表值为i的元素总和
 * select[i] 代表如果选数字i，从0处理到i的最大和
 * nonSelect[i]：代表如果不选数字i，从0处理到i的最大和
 * 
 * 如果选i，那么i-1肯定不选
 * 如果不选i，那么i-1选不选都可以，因此选择其中较大的选法
 */

public class DeleteAndEarn {

    public static int deleteAndEarn(int[] nums){
        if(nums.length == 0){
            return 0;
        }

        int[] sums = new int[10010];
        int[] select = new int[10010];
        int[] nonSelect = new int[10010];

        int maxValue = 0;
        for(int x : nums){
            sums[x] += x;
            maxValue = Math.max(maxValue, x);
        }

        for(int i = 1;i<=maxValue;i++){
            select[i] = nonSelect[i-1] + sums[i];
            nonSelect[i] = Math.max(select[i-1], nonSelect[i-1]);
        }
        System.out.println(Math.max(select[maxValue], nonSelect[maxValue]));
        return Math.max(select[maxValue], nonSelect[maxValue]);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3,4,2};
        deleteAndEarn(nums);
    }
    
}
