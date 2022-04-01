package Algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 20. 有效的括号
 * 
题目描述
给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
有效字符串需满足：左括号必须用相同类型的右括号闭合。左括号必须以正确的顺序闭合。

示例 1：
输入：s = "()"
输出：true

示例 2：
输入：s = "()[]{}"
输出：true

示例 3：
输入：s = "(]"
输出：false

示例 4：
输入：s = "([)]"
输出：false

示例 5：
输入：s = "{[]}"
输出：true

解题思路：遍历括号字符串，遇到左括号时，将左括号压入栈中，遇到右括号时，弹出栈顶元素（若栈为空直接返回false），判断是否是相同类型的括号。
若不匹配直接返回false。
 */

public class ValidParentheses {
    public boolean isValid(String str){
        char[] chars = str.toCharArray();
        Deque<Character> q = new ArrayDeque<>();
        for(char ch : chars){
            boolean left = ch == '(' || ch == '[' || ch == '{';
            if(left){
                q.push(ch);
            }else if(q.isEmpty() || !match(q.pop(), ch)) {
                return false;
            }
        }
        return q.isEmpty();
    }

    public boolean match(char l , char r){
        return (l == '(' && r == ')') || (l == '[' && r == ']') || (l == '{' && r == '}');
    }
    
}
