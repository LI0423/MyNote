package Algorithm.leetcode;

public class LongestCommonPrefix {
    
    public String longestCommonPrefix(String[] strs){
        if(strs == null || strs.length == 0){
            return "";
        }

        String prefix = strs[0];
        int count = strs.length;
        //横向对比字符串
        for (int i = 0; i < count; i++) {
            longestCommonPrefix(prefix, strs[i]);
            if(prefix.length()==0){
                break;
            }
        }
        return prefix;
    }

    public String longestCommonPrefix(String str1,String str2){
        int length = Math.min(str1.length(), str2.length()); //获取公共前缀的长度和下一个字符串长度的最小值，因为最长公共前缀不会超过字符串的长度。
        int index = 0;
        while(index<length && str1.charAt(index) == str2.charAt(index)){
            index ++;
        }
        return str1.substring(0, index);
    }

}
