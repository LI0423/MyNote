package Algorithm.leetcode;
/**
 * 面试题 59 - II. 队列的最大值
题目描述
请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都是O(1)。
若队列为空，pop_front 和 max_value 需要返回 -1

示例 1：
输入: 
["MaxQueue","push_back","push_back","max_value","pop_front","max_value"]
[[],[1],[2],[],[],[]]
输出: [null,null,null,2,1,2]

示例 2：
输入: 
["MaxQueue","pop_front","max_value"]
[[],[],[]]
输出: [null,-1,-1]
 */

import java.util.ArrayDeque;
import java.util.Deque;

public class MaxNumOfQueue {
    private Deque<Integer> p;
    private Deque<Integer> q;

    public MaxNumOfQueue(){
        p = new ArrayDeque<>();
        q = new ArrayDeque<>();
    }

    public int max_value(){
        return q.isEmpty() ? -1 : q.peekFirst();
    }

    public void push_back(int value){
        while(!q.isEmpty() && q.peekLast() < value){
            q.pollLast();
        }
        p.offerLast(value);
        q.offerLast(value);
    }

    public int pop_front(){
        if(p.isEmpty()){
            return -1;
        }
        int res = p.pollFirst();
        if(q.peek() == res){
            q.pollFirst();
        }
        return res;
    }
}
