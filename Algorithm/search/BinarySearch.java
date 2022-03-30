package Algorithm.search;

/**
 * 算法思想：每次划分一半进行下一步搜索。
 * 时间复杂度：O(logn)
 */

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

    
}
