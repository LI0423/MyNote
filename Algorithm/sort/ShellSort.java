package Algorithm.sort;

import java.util.Arrays;

/**
 * 把序列按下标的一定增量分组，对每组使用直接插入排序算法排序
 */

public class ShellSort {

    private static void shellSort(int[] nums){
        for (int gap = nums.length/2; gap>0 ; gap/=2) {
            for (int i = gap; i < nums.length; i++) {
                int key = nums[i];
                int j = i;
                while(j>gap && nums[j-gap]>key){
                    nums[j] =nums[j-gap];
                    j-=gap;
                }
                nums[j]=key;
            }
        }

    }

    public static void main(String[] args){
        int[] nums = {1,2,5,7,9,8};
        shellSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
