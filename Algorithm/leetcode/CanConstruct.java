package Algorithm.leetcode;

//383. 赎金信

// 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
// 如果可以，返回 true ；否则返回 false 。
// magazine 中的每个字符只能在 ransomNote 中使用一次。

// 示例 1：

// 输入：ransomNote = "a", magazine = "b"
// 输出：false
// 示例 2：

// 输入：ransomNote = "aa", magazine = "ab"
// 输出：false
// 示例 3：

// 输入：ransomNote = "aa", magazine = "aab"
// 输出：true

// 根据题意并利用两字符串只包含小写字母，先使用大小为字符集大小的数组（充当哈希表）对 magazinemagazinemagazine 进行词频统计，然后在遍历 ransomNoteransomNoteransomNote 时对计数数组进行抵消操作。

// 若处理过程中出现词频数量为负数，则说明 magazinemagazinemagazine 不能凑出 ransomNoteransomNoteransomNote。


public class CanConstruct {
    public boolean canConstruct(String ransomNote, String magazine){
        int[] cnt = new int[26];
        for (char c : ransomNote.toCharArray()) {
            cnt[c - 'a']++;
        }
        for(char c : magazine.toCharArray()){
            if(--cnt[c - 'a'] < 0){
                return false;
            }
        }
        return true;
    }
}
