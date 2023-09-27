package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//884. 两句话中的不常见单词

// 句子 是一串由空格分隔的单词。每个 单词 仅由小写字母组成。
// 如果某个单词在其中一个句子中恰好出现一次，在另一个句子中却 没有出现 ，那么这个单词就是 不常见的 。
// 给你两个 句子 s1 和 s2 ，返回所有 不常用单词 的列表。返回列表中单词可以按 任意顺序 组织。

// 示例 1：

// 输入：s1 = "this apple is sweet", s2 = "this apple is sour"
// 输出：["sweet","sour"]
// 示例 2：

// 输入：s1 = "apple apple", s2 = "banana"
// 输出：["banana"]

public class UncommonFromSentences {

    // 哈希表，把两句话中的所有单词都放入同一个hash表中，key为单词，value为个数，然后遍历hash表取出值为1的key。
    Map<String, Integer> map = new HashMap<>();
    public String[] uncommonFromSentences(String s1, String s2){
        addMap(s1);
        addMap(s2);
        List<String> res = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            if (entry.getValue() == 1) {
                res.add(entry.getKey());
            }
        }
        return res.toArray(new String[0]);
    }

    public void addMap(String s){
        String[] s1Arr = s.split(" ");
        for (String str : s1Arr){
            map.put(str, map.getOrDefault(str, 0) + 1);
        }
    }
    
}


//Python

// class UncommonFromSentences:
//     def uncommonFromSentences(self, s1: str, s2: str) -> List[str]:
//         freq = Counter(s1.split()) + Counter(s2.split())
//         ans = list()
//         for word, occ in freq.items():
//             if occ == 1:
//                 ans.append(word)
//         return ans


