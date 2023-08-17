package Algorithm.sort;

import java.util.Arrays;
/**
 * 比较相邻的元素，如果第一个比第二个大就交换，直到最后，做完后末尾的元素是最大的。
 * 时间复杂度：最好O(n)，最坏O(n^2)
 */

public class BubbleSort{

    public static void bubbleSort(int[] nums){
        for(int i = 0;i<nums.length;i++){
            for(int j = 0;j<nums.length-i-1;j++){
                if(nums[j]>nums[j+1]){
                    swap(nums, j, j+1);
                }
            }
        }
    }

    public static void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    public static void main(String[] args){
        int[] nums={1,2,7,5,9,8};
        bubbleSort(nums);
        System.out.println(Arrays.toString(nums));
    }

}

// python

// def bubble_sort(items, comp=lambda x, y : x > y):
//     item = items[:]
//     for i in range(len(items) - 1):
//         swapped = False
//         for j in range(len(items) - i - 1):
//             if comp(items[j], items[j+1]):
//                 items[j], items[j + 1] = items[j+1], items[j]
//                 swapped = True
//         if not swapped:
//             break
//     return items        
