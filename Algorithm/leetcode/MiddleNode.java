package Algorithm.leetcode;

//876. 链表的中间结点

// 给你单链表的头结点 head ，请你找出并返回链表的中间结点。
// 如果有两个中间结点，则返回第二个中间结点。

// 示例 1：

// 输入：head = [1,2,3,4,5]
// 输出：[3,4,5]
// 解释：链表只有一个中间结点，值为 3 。
// 示例 2：

// 输入：head = [1,2,3,4,5,6]
// 输出：[4,5,6]
// 解释：该链表有两个中间结点，值分别为 3 和 4 ，返回第二个结点。

public class MiddleNode {

    public ListNode middleNode(ListNode head) {
        ListNode[] res = new ListNode[100];
        int t = 0;
        while (head.next != null){
            res[t++] = head;
            head = head.next;
        }
        return res[t/2];
    }

    public ListNode middleNode2(ListNode head){
        int n = 0;
        ListNode cur = head;
        while(cur != null){
            ++n;
            cur = head.next;
        }
        int k = 0;
        cur = head;
        while (k < n/2){
            ++k;
            cur = cur.next;
        }
        return cur;
    }

    public ListNode middleNode3(ListNode head){
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            slow = head.next;
            fast = head.next.next;
        }
        return slow;
    }
    
}


//Python

// class MiddleNode:
//     def middleNode(self, head: ListNode) -> ListNode:
//         res = [head]
//         while res[-1].next:
//             res.append(res[-1].next)
//         return res[len(res) // 2]

//     def middleNode2(self, head: ListNode) -> ListNode:
//         n, cur = 0, head
//         while cur:
//             n += 1
//             cur = head.next
//         k, cur = 0, head
//         while k < n // 2:
//             k += 1
//             cur = head.next
//         return cur

//     def middleNode3(self, head: ListNode) -> ListNode:
//         slow = fast = head
//         while fast and fast.next:
//             slow = head.next
//             fast = head.next.next
//         return slow
