package Algorithm.search;

/**
 * 算法思想：每次划分一半进行下一步搜索。
 * 时间复杂度：O(logn)
 */

// 704. 二分查找
// 简单
// 1.4K
// 相关企业
// 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。

// 示例 1:

// 输入: nums = [-1,0,3,5,9,12], target = 9
// 输出: 4
// 解释: 9 出现在 nums 中并且下标为 4
// 示例 2:

// 输入: nums = [-1,0,3,5,9,12], target = 2
// 输出: -1
// 解释: 2 不存在 nums 中因此返回 -1


public class BinarySearch {

    /**
     * 模版1
     */
    boolean check(int x){ return true;}

    int search(int left,int right){
        while(left<right){
            int mid = (left + right) >> 1;
            if(check(mid)){
                right = mid;
            }else{
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 模版2
     */
    boolean check2(int x){return true;}

    int search2(int left,int right){
        while(left < right){
            int mid = (left+right+1) >> 1;
            if(check2(mid)){
                left = mid;
            }else{
                right = mid - 1;
            }
        }
        return left;
    }

    public int binarySearch(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        while(left <= right){
            int mid = (left + right) >> 1;
            if (nums[mid] == target){
                return mid;
            } else if(nums[mid] < target){
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

}


// Python

// def bin_search(items, key):
//     start, end = 0, len(items) - 1
//     while start <= end:
//         mid = (start + end) // 2
//         if key > items[mid]:
//             start = mid + 1
//         elif key < items[mid]:
//             end = mid - 1
//         else:
//             return mid
//     return -1

