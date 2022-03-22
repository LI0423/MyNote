package Algorithm.leetcode;

public class RotateArray {

    public static void rotate(int[] nums , int k){
        if(nums == null){
            return ;
        }
        int n = nums.length;
        k%=n;
        if(n<2 || k==0){
            return;
        }
        rotate(nums, 0, n-1);
        rotate(nums, 0, k-1);
        rotate(nums, k, n-1);

    }

    public static void rotate(int[] nums,int i,int j){
        while(i<j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            ++i;
            --j;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6,7};
        rotate(nums, 3);
    }
    
}
