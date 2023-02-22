package Algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

//225. 用队列实现栈

// 请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、top、pop 和 empty）。

// 实现 MyStack 类：

// void push(int x) 将元素 x 压入栈顶。
// int pop() 移除并返回栈顶元素。
// int top() 返回栈顶元素。
// boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。

// 注意：
// 你只能使用队列的基本操作 —— 也就是 push to back、peek/pop from front、size 和 is empty 这些操作。
// 你所使用的语言也许不支持队列。 你可以使用 list （列表）或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
 
// 示例：

// 输入：
// ["MyStack", "push", "push", "top", "pop", "empty"]
// [[], [1], [2], [], [], []]
// 输出：
// [null, null, null, 2, 2, false]

// 解释：
// MyStack myStack = new MyStack();
// myStack.push(1);
// myStack.push(2);
// myStack.top(); // 返回 2
// myStack.pop(); // 返回 2
// myStack.empty(); // 返回 False

public class MyStack {
    Queue<Integer> queue;

    public MyStack() {
        queue = new LinkedList<Integer>();
    }
    
    public void push(int x) {
        int n = queue.size();
        queue.offer(x);
        for (int i = 0; i < n; i++){
            queue.offer(queue.poll());
        }
    }
    
    public int pop() {
        return queue.poll();
    }
    
    public int top() {
        return queue.peek();
    }
    
    public boolean empty() {
        return queue.isEmpty();
    }
}

//Golang

// type MyStack() struct {
//     queue []int
// }

// func Constructor (s struct) {
//     return
// }

// func (s *MyStack) Push(x int) {
//     n := len(s.queue)
//     s.queue = append(s.queue, x)
//     for ; n > 0; n-- {
//         s.queue = append(s.queue, s.queue[0])
//         s.queue = s.queue[1:]
//     }
// }

// func (s *MyStack) Pop() int {
//     v := s.queue[0]
//     s.queue = s.queue[1:]
//     return v
// }

// func (s *MyStack) Top int {
//     return s.queue[0]
// }

// func(s *MyStack) Empty bool {
//     return len(s.queue) == 0
// }