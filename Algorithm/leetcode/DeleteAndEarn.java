package Algorithm.leetcode;

public class DeleteAndEarn {

    public int deleteAndEarn(int[] nums){
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
        return Math.max(select[maxValue], nonSelect[maxValue]);
    }
    
}
