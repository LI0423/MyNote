package Algorithm.leetcode;

//832. 翻转图像

// 给定一个 n x n 的二进制矩阵 image ，先 水平 翻转图像，然后 反转 图像并返回 结果 。
// 水平翻转图片就是将图片的每一行都进行翻转，即逆序。
// 例如，水平翻转 [1,1,0] 的结果是 [0,1,1]。
// 反转图片的意思是图片中的 0 全部被 1 替换， 1 全部被 0 替换。
// 例如，反转 [0,1,1] 的结果是 [1,0,0]。
 
// 示例 1：

// 输入：image = [[1,1,0],[1,0,1],[0,0,0]]
// 输出：[[1,0,0],[0,1,0],[1,1,1]]
// 解释：首先翻转每一行: [[0,1,1],[1,0,1],[0,0,0]]；
//      然后反转图片: [[1,0,0],[0,1,0],[1,1,1]]
// 示例 2：

// 输入：image = [[1,1,0,0],[1,0,0,1],[0,1,1,1],[1,0,1,0]]
// 输出：[[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
// 解释：首先翻转每一行: [[0,0,1,1],[1,0,0,1],[1,1,1,0],[0,1,0,1]]；
//      然后反转图片: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]

public class FlipAndInvertImage {
    public int[][] flipAndInvertImage(int[][] image) {
        int n = image[0].length;
        for(int k = 0; k < image.length; k++){
            int i = 0, j = image[0].length - 1;
            while (j > i){
                int tmp = 0;
                tmp = image[k][i];
                image[k][i] = image[k][j];
                image[k][j] = tmp;
                i++;
                j--;
            }
            for(int m = 0; m < n; m++){
                if (image[k][m] == 1){
                    image[k][m] = 0;
                } else {
                    image[k][m] = 1;
                }
            }
        }
        return image;
    }

    public int[][] flipAndInvertImage2(int[][] image) {
        for(int k = 0; k < image.length; k++){
            int i = 0, j = image.length - 1;
            while (j > i){
                if (image[k][i] == image[k][j]) {
                    image[k][i] ^= 1;
                    image[k][j] ^= 1;
                }
                i++;
                j--;
            }
            if (i == j) {
                image[k][i] ^= 1;
            }
        }
        return image;
    }
}


//Python

// class FlipAndInvertImage:
//     def flipAndInvertImage(self, image:List[List[int]]) -> List[List[int]]:
//         n = len(image)
//         for i in range(n):
//             left, right = 0, n - 1
//             while left < right:
//                 if image[i][left] == image[i][right]:
//                     image[i][left] ^= 1
//                     image[i][right] ^= 1
//                 left += 1
//                 right -= 1
//             if left == right:
//                 image[i][left] ^= 1
//         return image


//Golang

// func flipAndInvertImage(image [][]int) [][]int {
//     for _, row := range image {
//         left, right = len(row) - 1
//         for left < right{
//             if row[left] == row[right]{
//                 row[left] ^= 1
//                 row[right] ^= 1
//             }
//             left++
//             right--
//         }
//         if left == right {
//             row[left] ^= 1
//         }
//     }
//     return image
// }
