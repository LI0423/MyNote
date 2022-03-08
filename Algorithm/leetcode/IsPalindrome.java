package Algorithm.leetcode;
/**
 * 9.回文数
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。

回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。

例如，121 是回文，而 123 不是。
 
 
示例 1：

输入：x = 121
输出：true

 */
public class IsPalindrome {
    public boolean isPalindrome(int x){
        if (x<0 || (x%10 == 0 && x != 0)){
            return false;
        }

        int revertNumber = 0;
        while(x>revertNumber){
            revertNumber = revertNumber*10 + x%10;
            x /= 10;
        }

        //当长度为奇数时，可以通过revertNumber/10去除位于中位的数字。
        return x==revertNumber || x == revertNumber/10;
    }
}
