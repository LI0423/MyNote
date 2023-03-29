package Algorithm.leetcode;

//476. 数字的补数
// 对整数的二进制表示取反（0 变 1 ，1 变 0）后，再转换为十进制表示，可以得到这个整数的补数。

// 例如，整数 5 的二进制表示是 "101" ，取反后得到 "010" ，再转回十进制表示得到补数 2 。
// 给你一个整数 num ，输出它的补数。

// 示例 1：

// 输入：num = 5
// 输出：2
// 解释：5 的二进制表示为 101（没有前导零位），其补数为 010。所以你需要输出 2 。
// 示例 2：

// 输入：num = 1
// 输出：0
// 解释：1 的二进制表示为 1（没有前导零位），其补数为 0。所以你需要输出 0 。

public class FindComplement {
    public int findComplement(int n){
        int s = 1;
        while (s < n) {
            s <<= 1;
        }
        return (int) (s-1) ^ n;
    }

    public int findComplement2(int n){
        int s = -1;
        for(int i = 32; i >= 0; i--){
            if(((n >> i) & 1) != 0){
                s = i;
                break;
            }
        }

        int ans = 0;
        for(int j = 0; j < s; j++) {
            if(((n >> j) & 1) == 0){
                ans |= (1 << j);
            }
        }
        return ans;
    }
    //先对 num 进行「从高到低」的检查，找到最高位 1 的位置 s，然后再对 num 进行遍历，将低位到 s 位的位置执行逐位取反操作。

}
