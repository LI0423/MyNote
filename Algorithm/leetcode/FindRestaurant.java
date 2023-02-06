package Algorithm.leetcode;

// 599. 两个列表的最小索引总和
// 假设 Andy 和 Doris 想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。
// 你需要帮助他们用最少的索引和找出他们共同喜爱的餐厅。 如果答案不止一个，则输出所有答案并且不考虑顺序。 你可以假设答案总是存在。

// 示例 1:

// 输入: list1 = ["Shogun", "Tapioca Express", "Burger King", "KFC"]，list2 = ["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
// 输出: ["Shogun"]
// 解释: 他们唯一共同喜爱的餐厅是“Shogun”。
// 示例 2:

// 输入:list1 = ["Shogun", "Tapioca Express", "Burger King", "KFC"]，list2 = ["KFC", "Shogun", "Burger King"]
// 输出: ["Shogun"]
// 解释: 他们共同喜爱且具有最小索引和的餐厅是“Shogun”，它有最小的索引和1(0+1)。


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindRestaurant {
    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> index = new HashMap<String, Integer>();
        for (int i = 0; i < list1.length; i++) {
            index.put(list1[i], i);
        }

        List<String> ret = new ArrayList<String>();
        int indexSum = Integer.MAX_VALUE;
        for (int i = 0; i < list2.length; i++) {
            if (index.containsKey(list2[i])) {
                int j = index.get(list2[i]);
                if (i + j < indexSum) {
                    ret.clear();
                    ret.add(list2[i]);
                    indexSum = i + j;
                } else if (i + j == indexSum) {
                    ret.add(list2[i]);
                }
            }
        }
        return ret.toArray(new String[ret.size()]);
    }
}

// GO

// func findRestaurant(list1 []string, list2 []string) (ans []string) {
//     index := make(map[string]int, len(list1))
//     for i, s := range list1 {
//         index[s] = i
//     }
//     indexSum := math.MaxInt32
//     for i, s := range list2 {
//         if j, ok := index[s]; ok {
//             if i+j < indexSum {
//                 indexSum = i + j
//                 ans = []string{s}
//             } else if i+j == indexSum {
//                 ans = append(ans, s)
//             }
//         }
//     }
//     return ans
// }

//Python

// class Solution:
//     def findRestaurant(self, list1: List[str], list2: List[str]) -> List[str]:
//         index = {s: i for i, s in enumerate(list1)}
//         ans = []
//         indexSum = inf
//         for i, s in enumerate(list2):
//             if s in index:
//                 j = index[s]
//                 if i + j < indexSum:
//                     indexSum = i + j
//                     ans = [s]
//                 elif i + j == indexSum:
//                     ans.append(s)
//         return ans
