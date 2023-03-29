package Algorithm.leetcode;

//482. 密钥格式化
// 给定一个许可密钥字符串 s，仅由字母、数字字符和破折号组成。字符串由 n 个破折号分成 n + 1 组。你也会得到一个整数 k 。
// 我们想要重新格式化字符串 s，使每一组包含 k 个字符，除了第一组，它可以比 k 短，但仍然必须包含至少一个字符。此外，两组之间必须插入破折号，并且应该将所有小写字母转换为大写字母。
// 返回 重新格式化的许可密钥 。

// 示例 1：

// 输入：S = "5F3Z-2e-9-w", k = 4
// 输出："5F3Z-2E9W"
// 解释：字符串 S 被分成了两个部分，每部分 4 个字符；
//      注意，两个额外的破折号需要删掉。
// 示例 2：

// 输入：S = "2-5g-3-J", k = 2
// 输出："2-5G-3J"
// 解释：字符串 S 被分成了 3 个部分，按照前面的规则描述，第一部分的字符可以少于给定的数量，其余部分皆为 2 个字符。

public class LicenseKeyFormatting {
    public String licenseKeyFormatting(String s, int k){
        if(s == null || s == ""){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = s.length() - 1; i >= 0; i--){
            if(s.charAt(i) == '-') {
                continue;
            }
            if(count == k && count >= 0) {
                sb.append('-');
                count = 0;
            }
            sb.append(s.charAt(i));
            count++;
        }
        return sb.reverse().toString().toUpperCase();
    }
}

//Golang

// func licenseKeyFormatting(s string, k int) string { 
//     if s == "" { return "" } 
//     var sb strings.Builder 
//     count := 0 
//     for i := len(s) - 1; i >= 0; i-- {
//          if s[i] == '-' { 
//              continue 
//          } 
//          if count == k && count >= 0 {
//              sb.WriteByte('-') 
//              count = 0
//          } 
//          sb.WriteByte(s[i])
//          count++
//     }
//     b := []byte(sb.String()) 
//     for i := 0; i < len(b)/2; i++ { 
//         b[i], b[len(b)-i-1] = b[len(b)-i-1], b[i] 
//     } 
//     return strings.ToUpper(string(b)) 
// }
