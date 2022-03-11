package Algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

public class MajorityElement {

    public Map<Integer,Integer> countNums(int[] nums){
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(Integer num : nums){
            if(!map.containsKey(num)){
                map.put(num, 1);
            }else{
                map.put(num, map.get(num)+1);
            }
        }
        return map;
    }

    public int majorityElement(int[] nums){
        Map<Integer,Integer> counts = countNums(nums);
        Map.Entry<Integer,Integer> majorityEntry = null;

        for(Map.Entry<Integer,Integer> entry : counts.entrySet()){
            if(majorityEntry == null || entry.getValue()>majorityEntry.getValue()){
                majorityEntry = entry;
            }
        }
        return majorityEntry.getKey();
    }
}

// 摩尔投票法
/**public int majorityElement(int[] nums) {
    int count =0,major = 0;
    for (int i : nums) {
        if(count == 0){
            major = i;
        }else{
            count+=(major == i?1:-1);
        }
    }
    return major;
}
**/