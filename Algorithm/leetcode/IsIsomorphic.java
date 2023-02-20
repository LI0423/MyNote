package Algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsIsomorphic{
    public static boolean isIsomorphic(String s, String t) {
        String str1 = getNumStr(s);
        String str2 = getNumStr(t);
        return str1.equals(str2);
    }

    public static String getNumStr(String str){
        Map<Character,Integer> hashMap = new HashMap<>();
        char[] chars = str.toCharArray();
        int i = 1;
        List<Integer> list = new ArrayList<>();
        for (char c : chars) {
            if(hashMap.containsKey(c)){
                list.add(hashMap.get(c));
                continue;
            } else {
                hashMap.put(c, i);
                list.add(i);
            }
            i++;
        }
        return list.toString();
    }

    public boolean isIsomorphic2(String s, String t) {
        for (int i = 0; i < s.length(); i++){
            if(s.indexOf(s.charAt(i)) != t.indexOf(t.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isIsomorphic3(String s, String t) {
        Map<Character, Character> s2t = new HashMap<Character, Character>();
        Map<Character, Character> t2s = new HashMap<Character, Character>();
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            char x = s.charAt(i), y = t.charAt(i);
            if ((s2t.containsKey(x) && s2t.get(x) != y) || (t2s.containsKey(y) && t2s.get(y) != x)) {
                return false;
            }
            s2t.put(x, y);
            t2s.put(y, x);
        }
        return true;
    }
}

//Golang

// func isIsomorphic (s, t string) bool{
//     s2t := map[byte]byte{}
//     t2s := map[byte]byte{}
//     for i := range s {
//         x, y := s[i], t[i]
//         if s2t[x] > 0 && s2t[x] != y || t2s[y] > 0 && t2s[y] != x {
//             return false
//         }
//         s2t[x] = y
//         t2s[y] = x
//     }
//     return true
// }