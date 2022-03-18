package Algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

public class LengthOfLongestSubstring {

    public int lengthOfLongestSubstring(String s){

        int i = 0, j = 0, count = 0;
        Set<Character> chars = new HashSet<>();
        for(char c : s.toCharArray()){
            while(chars.contains(c)){
                chars.remove(s.charAt(i++));
            }
            chars.add(c);
            count = Math.max(count, j-i+1);
            ++j;
        }
        return count;
    }
    
}
