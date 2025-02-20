package Algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

import Algorithm.DataStructure.ListNode;

//141. 环形链表
// 给你一个链表的头节点 head ，判断链表中是否有环。
// 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。注意：pos 不作为参数进行传递。
// 如果链表中存在环 ，则返回 true。否则，返回 false 。

// 示例 1：

// 输入：head = [3,2,0,-4], pos = 1
// 输出：true
// 解释：链表中有一个环，其尾部连接到第二个节点。
// 示例 2：

// 输入：head = [1,2], pos = 0
// 输出：true
// 解释：链表中有一个环，其尾部连接到第一个节点。
// 示例 3：

// 输入：head = [1], pos = -1
// 输出：false
// 解释：链表中没有环。


public class HasCycle {
    public boolean hasCycle(ListNode head){
        Set<ListNode>  set = new HashSet<ListNode>();
        while(head != null){
            if (!set.add(head)){
                return true;
            }
            head = head.next;
        }
        return false;
    }

    public boolean hasCycle2(ListNode head){
        if (head == null || head.next == null){
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while(slow != fast){
            if (fast == null || fast.next == null) {
                return false ;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}

//Golang

// type ListNode struct {
//     Val int
//     Next *ListNode
// }

// func hasCycle(head *ListNode) bool{
//     set := map[*ListNode]struct{}{}
//     for head != nil {
//         if _, ok := set[head]; ok {
//             return true
//         }
//         set[head] = struct{}{}
//         head = head.Next
//     }
//     return false 
// }

// func hasCycle2(head *ListNode) bool {
//     if head == nil || head.next == nil {
//         return false
//     }
//     slow, fast := head, head.Next
//     for fast != slow {
//         if fast == nil || fast.Next == nil {
//             return false
//         }
//         slow = slow.Next
//         fast = fast.Next.Next
//     }
//     return true
// }
