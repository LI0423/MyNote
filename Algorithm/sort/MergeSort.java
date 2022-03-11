package Algorithm.sort;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MergeSort {

    private static int[] mergeSort(int[] nums,int left , int right){
        if(left>=right){
            return nums;
        }

        int mid = (left+right)>>>1;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid+1, right);
        int[] temp = new int[nums.length];
        int i =left,j=mid+1,k=0;
        while(i<=mid && j<= right){
            if(nums[i]<=nums[j]){
                temp[k++] = nums[i++];
            }else{
                temp[k++] = nums[j++];
            }
        }
        while(i<=mid){
            temp[k++] = nums[i++];
        }
        while(j<=right){
            temp[k++] = nums[j++];
        }
        for(i=left,j=0;i<=right;++i,++j){
            nums[i] = temp[j];
        }
        return nums;
    }

    public static void main(String[] args) {
        
        int[] nums = new int[]{9,8,7,6,5,4,3,2,1};
        mergeSort(nums, 0, nums.length-1);
        System.out.println(Arrays.toString(nums));

    }
}
