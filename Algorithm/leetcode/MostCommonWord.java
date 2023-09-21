package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//819. 最常见的单词

// 给定一个段落 (paragraph) 和一个禁用单词列表 (banned)。返回出现次数最多，同时不在禁用列表中的单词。
// 题目保证至少有一个词不在禁用列表中，而且答案唯一。
// 禁用列表中的单词用小写字母表示，不含标点符号。段落中的单词不区分大小写。答案都是小写字母。

// 示例：

// 输入: 
// paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
// banned = ["hit"]
// 输出: "ball"
// 解释: 
// "hit" 出现了3次，但它是一个禁用的单词。
// "ball" 出现了2次 (同时没有其他单词出现2次)，所以它是段落里出现次数最多的，且不在禁用列表中的单词。 
// 注意，所有这些单词在段落里不区分大小写，标点符号需要忽略（即使是紧挨着单词也忽略， 比如 "ball,"）， 
// "hit"不是最终的答案，虽然它出现次数更多，但它在禁用单词列表中。

public class MostCommonWord {
    public String mostCommonWord(String paragraph, String[] banned) {
        String lowerCase = paragraph.toLowerCase();
        List<String> list = new ArrayList<>();
        for(String banStr : banned){
            list.add(banStr);
        }
        Map<String,Integer> map = new HashMap<String, Integer>();
        char[] charArray = lowerCase.toCharArray();
        int n = charArray.length;
        String ans = null;
        for (int i = 0; i < charArray.length;) {
            if (!Character.isLetter(charArray[i]) && ++i >= 0){
                continue;
            }
            int j = i;
            while(j < n && Character.isLetter(charArray[j])){
                j++;
            }
            String subString = lowerCase.substring(i, j);
            i = j + 1;
            if(list.contains(subString)){
                continue;
            }
            map.put(subString, map.getOrDefault(subString, 0) + 1);
            if (ans == null || map.get(subString) > map.get(ans)){
                ans = subString;
            }
        }
        return ans;
    }
}
