package Algorithm.leetcode;

public class RemoveElement {

    private static int removeElement(int[] nums,int target){
        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == target){
                ++cnt;
            }else{
                nums[i-cnt] = nums[i];
            }
        }
        return nums.length-cnt;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,2,2,3};
        removeElement(nums, 2);
    }
    
}
